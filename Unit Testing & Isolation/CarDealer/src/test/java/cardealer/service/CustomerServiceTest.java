package cardealer.service;

import cardealer.domain.entities.Customer;
import cardealer.domain.models.service.CustomerServiceModel;
import cardealer.repository.CustomerRepository;
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
public class CustomerServiceTest {
    @Autowired
    private CustomerRepository customerRepository;
    private ModelMapper modelMapper;
    private CustomerServiceImpl customerService;
    private CustomerServiceModel customer;

    @Before
    public void setUp() throws Exception {
        this.modelMapper = new ModelMapper();
        this.customerService = new CustomerServiceImpl(this.customerRepository, this.modelMapper);
        this.customer = new CustomerServiceModel();
        this.customer.setName("Emil");
        this.customer.setBirthDate(LocalDate.of(1979, 4, 28));
        this.customer.setYoungDriver(false);
    }

    @Test
    public void customerService_saveCustomerWithCorrectValues_returnsCorrect() {
        CustomerServiceModel actual = this.customerService.saveCustomer(customer);
        CustomerServiceModel expected = this.modelMapper.map(this.customerRepository.findAll().get(0), CustomerServiceModel.class);
        Assert.assertEquals(actual.getName(), expected.getName());
        Assert.assertEquals(actual.getBirthDate(), expected.getBirthDate());
        Assert.assertEquals(actual.isYoungDriver(), expected.isYoungDriver());
    }

    @Test(expected = Exception.class)
    public void customerService_saveCustomerWithNullValues_throwsException() {
        CustomerServiceModel toBeSaved = new CustomerServiceModel();
        toBeSaved.setName(null);
        toBeSaved.setBirthDate(null);
        toBeSaved.setYoungDriver(false);
        CustomerServiceModel actual = this.customerService.saveCustomer(toBeSaved);
        CustomerServiceModel expected = this.modelMapper.map(this.customerRepository.findAll().get(0), CustomerServiceModel.class);
        Assert.assertEquals(actual.getName(), expected.getName());
        Assert.assertEquals(actual.getBirthDate(), expected.getBirthDate());
        Assert.assertEquals(actual.isYoungDriver(), expected.isYoungDriver());
    }


    @Test
    public void customerService_editCustomerWithCorrectValues_returnsCorrect() {
        customer = this.customerService.saveCustomer(customer);
        customer.setId(customer.getId());
        customer.setName("Pavel");
        customer.setBirthDate(LocalDate.of(1980, 1, 4));
        customer.setYoungDriver(true);
        CustomerServiceModel actual = this.customerService.editCustomer(
                this.modelMapper.map(customer, CustomerServiceModel.class));
        CustomerServiceModel expected = this.modelMapper.map(this.customerRepository.findAll().get(0), CustomerServiceModel.class);
        Assert.assertEquals(actual.getName(), expected.getName());
        Assert.assertEquals(actual.getBirthDate(), expected.getBirthDate());
        Assert.assertEquals(actual.isYoungDriver(), expected.isYoungDriver());

    }

    @Test(expected = Exception.class)
    public void customerService_editCustomerWithNullValues_throwsException() {
        customer = this.customerService.saveCustomer(customer);
        customer.setId(customer.getId());
        customer.setName(null);
        customer.setBirthDate(null);
        customer.setYoungDriver(true);
        CustomerServiceModel actual = this.customerService.editCustomer(
                this.modelMapper.map(customer, CustomerServiceModel.class));
    }

    @Test
    public void customerService_deleteCustomer_checkIsDeleted() {
        customer = this.customerService.saveCustomer(customer);
        CustomerServiceModel actual = this.customerService.deleteCustomer(customer.getId());
        Customer expected = this.customerRepository.findById(customer.getId()).orElse(null);
        Assert.assertNotNull(actual);
        Assert.assertNull(expected);
    }

    @Test(expected = Exception.class)
    public void customerService_deleteCustomer_throwsException() {
        this.customerService.saveCustomer(customer);
        CustomerServiceModel actual = this.customerService.deleteCustomer("invalidID");
    }

    @Test
    public void customerService_findCustomerWithCorrectId_returnsCorrectCar() {
        customer = this.customerService.saveCustomer(customer);
        CustomerServiceModel actual = this.customerService.findCustomerById(customer.getId());
        Assert.assertEquals(customer.getId(), actual.getId());
        Assert.assertEquals(customer.getName(), actual.getName());
        Assert.assertEquals(customer.getBirthDate(), actual.getBirthDate());
    }

    @Test(expected = Exception.class)
    public void customerService_findCustomerWithIncorrectId_throwsException() {
        customer = this.customerService.saveCustomer(customer);
        CustomerServiceModel actual = this.customerService.findCustomerById("invalidID");
    }
}