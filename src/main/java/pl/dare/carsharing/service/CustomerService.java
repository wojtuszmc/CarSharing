package pl.dare.carsharing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dare.carsharing.jpa.Customer;
import pl.dare.carsharing.model.AddCustomerRequest;
import pl.dare.carsharing.model.CustomerDto;
import pl.dare.carsharing.model.EditCustomerRequest;
import pl.dare.carsharing.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public void addCustomer(AddCustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setLastName(request.getLastName());
        customerRepository.save(customer);
    }

    public List<CustomerDto> getCustomers() {
        List<CustomerDto> customersDto = new ArrayList<>();
        List<Customer> customers = customerRepository.findAll();
        for (Customer customer: customers) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(customer.getId());
            customerDto.setName(customer.getName());
            customerDto.setLastName(customer.getLastName());
            customersDto.add(customerDto);
        }
        return customersDto;
    }

    public void addCustomers(List<CustomerDto> customersToAdd) {
        getCustomers().addAll(customersToAdd);
    }

    public CustomerDto getCustomerById(Long id) { // Zapytaj
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setLastName(customer.getLastName());
        return customerDto;
    }

    public void removeCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public void updateCustomer(Long id, EditCustomerRequest request) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        customer.setName(request.getName());
        customer.setLastName(request.getLastName());
        customerRepository.save(customer);
    }
}
