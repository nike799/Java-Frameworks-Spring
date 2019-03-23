package cardealer.service;

import cardealer.domain.entities.Car;
import cardealer.domain.entities.CarSale;
import cardealer.domain.entities.Customer;
import cardealer.domain.models.service.CarSaleServiceModel;
import cardealer.domain.models.service.CarServiceModel;
import cardealer.repository.CarRepository;
import cardealer.repository.CarSaleRepository;
import cardealer.repository.CustomerRepository;
import cardealer.repository.PartSaleRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SaleServiceTest {
    @Autowired
    private PartSaleRepository partSaleRepository;
    @Autowired
    private CarSaleRepository carSaleRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CustomerRepository customerRepository;
    private SaleServiceImpl saleService;
    private ModelMapper modelMapper;

    @Before
    public void setUp() throws Exception {
        this.modelMapper = new ModelMapper();
        this.saleService = new SaleServiceImpl(this.carSaleRepository,this.partSaleRepository,this.modelMapper);
    }

    @Test
    public void saleService_saleCarWithCorrectValues_returnsCorrect(){
        CarSaleServiceModel carSaleServiceModel = new CarSaleServiceModel();
        Car car = new Car();
        car.setMake("Peugeot");
        car.setModel("307");
        car.setTravelledDistance(148000L);
        car = this.carRepository.save(car);
        CarSale carSale = new CarSale();
        carSale.setCar(car);
        Customer customer = new Customer();
        customer.setName("Emil");
        customer.setBirthDate(LocalDate.of(1979,4,28));
        customer.setYoungDriver(false);
        customer = this.customerRepository.save(customer);
        carSale.setCustomer(customer);
        carSale.setDiscount(5.0);
        this.carSaleRepository.save(carSale);
        CarServiceModel carServiceModel = new CarServiceModel();
        carSaleServiceModel.setCar(this.modelMapper.map(car,CarServiceModel.class));
        carSaleServiceModel = this.saleService.saleCar(carSaleServiceModel);

        Assert.assertTrue(this.carSaleRepository.findAll().size()>0);
    }
}