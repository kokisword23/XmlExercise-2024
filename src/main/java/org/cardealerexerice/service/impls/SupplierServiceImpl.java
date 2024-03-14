package org.cardealerexerice.service.impls;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.cardealerexerice.data.entities.Supplier;
import org.cardealerexerice.data.repositories.SupplierRepository;
import org.cardealerexerice.service.SupplierService;
import org.cardealerexerice.service.dtos.exports.SupplierLocalDto;
import org.cardealerexerice.service.dtos.exports.SupplierLocalRootDto;
import org.cardealerexerice.service.dtos.imports.SupplierSeedDto;
import org.cardealerexerice.service.dtos.imports.SupplierSeedRootDto;
import org.cardealerexerice.util.ValidationUtil;
import org.cardealerexerice.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private static final String FILE_IMPORT_PATH = "src/main/resources/xml/imports/suppliers.xml";
    private static final String FILE_EXPORT_LOCAL_PATH = "src/main/resources/xml/exports/local-supplier.xml";

    private final SupplierRepository supplierRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public SupplierServiceImpl(SupplierRepository supplierRepository, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedSupplier() throws JAXBException {
        if (this.supplierRepository.count() == 0) {
            SupplierSeedRootDto supplierSeedRootDto = xmlParser.parse(SupplierSeedRootDto.class, FILE_IMPORT_PATH);
            for (SupplierSeedDto supplierSeedDto : supplierSeedRootDto.getSupplierSeedDtoList()) {
                if (!this.validationUtil.isValid(supplierSeedDto)) {
                    System.out.println("Invalid supplier data");

                    continue;
                }

                Supplier supplier = this.modelMapper.map(supplierSeedDto, Supplier.class);
                this.supplierRepository.saveAndFlush(supplier);
            }
        }
    }

    @Override
    public void exportLocalSuppliers() throws JAXBException {
        List<SupplierLocalDto> supplierLocalDtos = this.supplierRepository.findAllByIsImporter(false)
                .stream()
                .map(s -> {
                    SupplierLocalDto dto = this.modelMapper.map(s, SupplierLocalDto.class);
                    dto.setPartsCount(s.getParts().size());
                    return dto;
                })
                .collect(Collectors.toList());

        SupplierLocalRootDto supplierLocalRootDto = new SupplierLocalRootDto();
        supplierLocalRootDto.setSupplierLocalDto(supplierLocalDtos);

        this.xmlParser.exportToFile(SupplierLocalRootDto.class, supplierLocalRootDto, FILE_EXPORT_LOCAL_PATH);
    }
}
