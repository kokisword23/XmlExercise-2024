package org.cardealerexerice.service.impls;

import jakarta.xml.bind.JAXBException;
import org.cardealerexerice.data.entities.Car;
import org.cardealerexerice.data.entities.Customer;
import org.cardealerexerice.data.entities.Part;
import org.cardealerexerice.data.entities.Sale;
import org.cardealerexerice.data.repositories.CarRepository;
import org.cardealerexerice.data.repositories.CustomerRepository;
import org.cardealerexerice.data.repositories.SaleRepository;
import org.cardealerexerice.service.SaleService;
import org.cardealerexerice.service.dtos.exports.CarDto;
import org.cardealerexerice.service.dtos.exports.SaleDiscountDto;
import org.cardealerexerice.service.dtos.exports.SaleDiscountsRootDto;
import org.cardealerexerice.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {

    private final List<Double> discounts = List.of(1.0, 0.95, 0.9, 0.85, 0.8, 0.7, 0.6, 0.5);

    private final SaleRepository saleRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private XmlParser xmlParser;
    private ModelMapper mapper;

    public SaleServiceImpl(SaleRepository saleRepository, CarRepository carRepository, CustomerRepository customerRepository, XmlParser xmlParser, ModelMapper mapper) {
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.xmlParser = xmlParser;
        this.mapper = mapper;
    }

    @Override
    public void seedSales() {
        if (this.saleRepository.count() == 0) {
            for (int i = 0; i < 50 ; i++) {
                Sale sale = new Sale();
                sale.setCar(getRandomCar());
                sale.setCustomer(getRandomCustomer());
                sale.setDiscount(getRandomDiscount());
                this.saleRepository.saveAndFlush(sale);
            }
        }
    }

    @Override
    public void exportSales() throws JAXBException {
        List<SaleDiscountDto> saleDiscountDtos = this.saleRepository
                .findAll()
                .stream()
                .map(s -> {
                    SaleDiscountDto saleDiscountDto = this.mapper.map(s, SaleDiscountDto.class);
                    CarDto car = this.mapper.map(s.getCar(), CarDto.class);

                    saleDiscountDto.setCarDto(car);
                    saleDiscountDto.setCustomerName(s.getCustomer().getName());
                    saleDiscountDto.setPrice(s.getCar().getParts().stream().map(Part::getPrice).reduce(BigDecimal::add).get());
                    saleDiscountDto.setPriceWithDiscount(saleDiscountDto.getPrice().multiply(BigDecimal.valueOf(s.getDiscount())));
                    return saleDiscountDto;
                })
                .collect(Collectors.toList());

        SaleDiscountsRootDto saleDiscountsRootDto = new SaleDiscountsRootDto();
        saleDiscountsRootDto.setSaleDiscountDtos(saleDiscountDtos);

        this.xmlParser.exportToFile(SaleDiscountsRootDto.class, saleDiscountsRootDto, "src/main/resources/xml/exports/sale.xml");
    }

    private double getRandomDiscount() {
        return discounts.get(ThreadLocalRandom.current().nextInt(1, discounts.size()));
    }

    private Customer getRandomCustomer() {
        return this.customerRepository.findById(
                ThreadLocalRandom.current().nextLong(1, this.customerRepository.count() + 1)).get();
    }

    private Car getRandomCar() {
        return this.carRepository.findById(
                ThreadLocalRandom.current().nextLong(1, this.carRepository.count() + 1)).get();
    }
}
