package cardealer.service;

import cardealer.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import cardealer.domain.entities.Customer;
import cardealer.domain.models.service.CustomerServiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerServiceModel saveCustomer(CustomerServiceModel customerServiceModel) {
        Customer customer = this.modelMapper.map(customerServiceModel, Customer.class);
        customer = this.customerRepository.saveAndFlush(customer);

        return this.modelMapper.map(customer, CustomerServiceModel.class);

    }

    @Override
    public CustomerServiceModel editCustomer(CustomerServiceModel customerServiceModel) {
        Customer customer = this.customerRepository.findById(customerServiceModel.getId()).orElse(null);
        customer.setName(customerServiceModel.getName());
        customer.setBirthDate(customerServiceModel.getBirthDate());
        customer.setYoungDriver(customerServiceModel.isYoungDriver());

        customer = this.customerRepository.saveAndFlush(customer);

        return this.modelMapper.map(customer, CustomerServiceModel.class);

    }

    @Override
    public CustomerServiceModel deleteCustomer(String id) {
        Customer customer = this.customerRepository.findById(id).orElse(null);
        this.customerRepository.delete(customer);

        return this.modelMapper.map(customer, CustomerServiceModel.class);
    }

    @Override
    public CustomerServiceModel findCustomerById(String id) {
        Customer customer = this.customerRepository.findById(id).orElse(null);

        return this.modelMapper.map(customer, CustomerServiceModel.class);
    }
}
