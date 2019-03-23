package cardealer.service;

import cardealer.domain.entities.Car;
import cardealer.domain.models.service.CarServiceModel;
import cardealer.repository.CarRepository;
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

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CarServiceTest {
    @Autowired
    private CarRepository carRepository;
    private ModelMapper modelMapper;
    private CarServiceImpl carService;
    private Car car;

    @Before
    public void setUp() throws Exception {
        this.modelMapper = new ModelMapper();
        this.carService = new CarServiceImpl(this.carRepository, this.modelMapper);
        this.car = new Car();
        this.car.setMake("Peugeot");
        this.car.setModel("307");
        this.car.setTravelledDistance(100000L);
    }

    @Test
    public void carService_saveCarWithCorrectValues_returnsCorrect() {
        car.setTravelledDistance(100000L);
        CarServiceModel actual = this.carService.saveCar(this.modelMapper.map(car, CarServiceModel.class));
        CarServiceModel expected = this.modelMapper.map(this.carRepository.findAll().get(0), CarServiceModel.class);
        Assert.assertEquals(actual.getMake(), expected.getMake());
        Assert.assertEquals(actual.getModel(), expected.getModel());
        Assert.assertEquals(actual.getTravelledDistance(), expected.getTravelledDistance());
    }

    @Test(expected = Exception.class)
    public void carService_saveCarWithNullValues_throwsException() {
        CarServiceModel toBeSaved = new CarServiceModel();
        toBeSaved.setMake(null);
        toBeSaved.setModel(null);
        toBeSaved.setTravelledDistance(null);
        CarServiceModel actual = this.carService.saveCar(toBeSaved);
        CarServiceModel expected = this.modelMapper.map(this.carRepository.findAll().get(0), CarServiceModel.class);
        Assert.assertEquals(actual.getMake(), expected.getMake());
        Assert.assertEquals(actual.getModel(), expected.getModel());
        Assert.assertEquals(actual.getTravelledDistance(), expected.getTravelledDistance());
    }


    @Test
    public void carService_editCarWithCorrectValues_returnsCorrect() {
        car = this.carRepository.save(car);
        car.setMake("Renault");
        car.setModel("Laguna");
        car.setTravelledDistance(10L);
        CarServiceModel actual = this.carService.editCar(
                this.modelMapper.map(car, CarServiceModel.class));
        CarServiceModel expected = this.modelMapper.map(this.carRepository.findAll().get(0), CarServiceModel.class);
        Assert.assertEquals(actual.getMake(), expected.getMake());
        Assert.assertEquals(actual.getModel(), expected.getModel());
        Assert.assertEquals(actual.getTravelledDistance(), expected.getTravelledDistance());

    }

    @Test(expected = Exception.class)
    public void carService_editCarWithNullValues_throwsException() {
        car = this.carRepository.save(car);
        car.setMake(null);
        car.setModel(null);
        car.setTravelledDistance(10L);
        CarServiceModel actual = this.carService.editCar(
                this.modelMapper.map(car, CarServiceModel.class));
    }

    @Test
    public void carService_deleteCar_checkIsDeleted() {
        car = this.carRepository.save(car);
        CarServiceModel actual = this.carService.deleteCar(car.getId());
        Car expected = this.carRepository.findById(car.getId()).orElse(null);
        Assert.assertNotNull(actual);
        Assert.assertNull(expected);
    }

    @Test(expected = Exception.class)
    public void carService_deleteCar_throwsException() {
        car = this.carRepository.save(car);
        CarServiceModel actual = this.carService.deleteCar("invalidID");
    }

    @Test
    public void carService_findCarWithCorrectId_returnsCorrectCar() {
        car = this.carRepository.save(car);
        CarServiceModel actual = this.carService.findCarById(car.getId());
        Assert.assertEquals(car.getId(), actual.getId());
        Assert.assertEquals(car.getMake(), actual.getMake());
        Assert.assertEquals(car.getModel(), actual.getModel());
        Assert.assertEquals(car.getTravelledDistance(), actual.getTravelledDistance());
    }

    @Test(expected = Exception.class)
    public void supplierService_findSupplierWithIncorrectId_throwsException() {
        car = this.carRepository.save(car);
        CarServiceModel actual = this.carService.findCarById("invalidID");
    }
}