package pl.dare.carsharing.repository;

import pl.dare.carsharing.jpa.Car;
import pl.dare.carsharing.jpa.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    private List<Customer> customerDatabse = new ArrayList<>();

    public List<Customer> getAllCustomers() {
        return customerDatabse;
    }

    public void addCustomer(Customer customer) {
        customerDatabse.add(customer);
    }

    public void removeCustomer(int id) {
        customerDatabse.remove(id - 1);
    }
}
