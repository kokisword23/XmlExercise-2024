package org.cardealerexerice.service.impls;

import jakarta.xml.bind.JAXBException;
import org.cardealerexerice.data.entities.Car;
import org.cardealerexerice.data.entities.Part;
import org.cardealerexerice.data.repositories.CarRepository;
import org.cardealerexerice.data.repositories.PartRepository;
import org.cardealerexerice.service.CarService;
import org.cardealerexerice.service.dtos.CarSeedDto;
import org.cardealerexerice.service.dtos.CarSeedRootDto;
import org.cardealerexerice.service.dtos.exports.*;
import org.cardealerexerice.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private static final String FILE_IMPORT_PATH = "src/main/resources/xml/imports/cars.xml";
    private static final String FILE_EXPORT_TOYOTA_PATH = "src/main/resources/xml/exports/toyota-cars.xml";
    private static final String FILE_EXPORT_CARS_AND_PARTS_PATH = "src/main/resources/xml/exports/cars-parts.xml";

    private final CarRepository carRepository;
    private final PartRepository partRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    public CarServiceImpl(CarRepository carRepository, PartRepository partRepository, XmlParser xmlParser, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.partRepository = partRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCars() throws JAXBException {
        if (this.carRepository.count() == 0) {
            CarSeedRootDto carSeedRootDto = this.xmlParser.parse(CarSeedRootDto.class, FILE_IMPORT_PATH);
            for (CarSeedDto carSeedDto : carSeedRootDto.getCarSeedDtoList()) {
                Car car = this.modelMapper.map(carSeedDto, Car.class);
                car.setParts(getRandomParts());

               this.carRepository.saveAndFlush(car);
            }
        }
    }

    @Override
    public void exportToyotaCars() throws JAXBException {
        List<CarToyotaDto> toyotaDtos = this.carRepository.findAllByMakeOrderByTravelledDistanceDesc("Toyota")
                .stream()
                .map(c -> this.modelMapper.map(c, CarToyotaDto.class))
                .collect(Collectors.toList());

        CarToyotaRootDto carToyotaRootDto = new CarToyotaRootDto();
        carToyotaRootDto.setCarToyotaDtoList(toyotaDtos);

        this.xmlParser.exportToFile(CarToyotaRootDto.class, carToyotaRootDto, FILE_EXPORT_TOYOTA_PATH);
    }

    @Override
    public void exportCarsAndParts() throws JAXBException {
        List<CarAndPartsDto> carAndPartsDtos = this.carRepository.findAll()
                .stream()
                .map(c -> {
                    CarAndPartsDto carAndPartsDto = this.modelMapper.map(c, CarAndPartsDto.class);

                    PartRootDto partRootDto = new PartRootDto();
                    List<PartDto> partDtos = c.getParts()
                            .stream()
                            .map(p -> this.modelMapper.map(p, PartDto.class))
                            .collect(Collectors.toList());
                    partRootDto.setPartDtos(partDtos);

                    carAndPartsDto.setPartRootDto(partRootDto);
                    return carAndPartsDto;
                })
                .collect(Collectors.toList());

        CarAndPartsRootDto carAndPartsRootDto = new CarAndPartsRootDto();
        carAndPartsRootDto.setCarAndPartsDtoList(carAndPartsDtos);

        this.xmlParser.exportToFile(CarAndPartsRootDto.class, carAndPartsRootDto, FILE_EXPORT_CARS_AND_PARTS_PATH);
    }

    private Set<Part> getRandomParts() {
        Set<Part> parts = new HashSet<>();

        int count = ThreadLocalRandom.current().nextInt(2, 4);

        for (int i = 0; i < count ; i++) {
            parts.add(this.partRepository.findById(
                    ThreadLocalRandom.current().nextLong(1, this.partRepository.count() + 1)).get());
        }

        return parts;
    }
}
