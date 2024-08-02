package pl.dare.carsharing.service;

import pl.dare.carsharing.jpa.Customer;
import pl.dare.carsharing.model.AddCustomerRequest;
import pl.dare.carsharing.model.CustomerDto;
import pl.dare.carsharing.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private CustomerRepository customerRepository = new CustomerRepository();

    public void addCustomer(AddCustomerRequest request) {
        Customer customer = new Customer();
        customer.setId(request.getId());
        customer.setName(request.getName());
        customer.setLastName(request.getLastName());
        customerRepository.addCustomer(customer);
    }

    public List<CustomerDto> getCustomers() {
        List<CustomerDto> customersDto = new ArrayList<>();
        List<Customer> customers = customerRepository.getAllCustomers();
        for (Customer customer: customers) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(customer.getId());
            customerDto.setName(customer.getName());
            customerDto.setLastName(customer.getLastName());
            customersDto.add(customerDto);
        }
        return customersDto;
    }
}
