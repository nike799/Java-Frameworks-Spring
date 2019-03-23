package cardealer.service;

import cardealer.domain.models.service.CustomerServiceModel;

public interface CustomerService {

    CustomerServiceModel saveCustomer(CustomerServiceModel customerServiceModel);

    CustomerServiceModel editCustomer(CustomerServiceModel customerServiceModel);

    CustomerServiceModel deleteCustomer(String id);

    CustomerServiceModel findCustomerById(String id);
}
