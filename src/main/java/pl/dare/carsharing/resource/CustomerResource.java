package pl.dare.carsharing.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.dare.carsharing.model.AddCustomerRequest;
import pl.dare.carsharing.model.CustomerDto;
import pl.dare.carsharing.model.CustomersResponse;
import pl.dare.carsharing.model.EditCustomerRequest;
import pl.dare.carsharing.service.CustomerService;

import java.util.List;

@AllArgsConstructor
@ToString
@NoArgsConstructor
@Data

@RestController
@RequestMapping("customers")
public class CustomerResource {
    @Autowired
    private CustomerService customerService;


    @GetMapping
    public CustomersResponse getCustomers() {
        CustomersResponse customersResponse = new CustomersResponse();
        customersResponse.setCustomers(customerService.getCustomers());
        return customersResponse;
    }

    @PostMapping
    public void addCustomer(@RequestBody AddCustomerRequest request) {
        customerService.addCustomer(request);
    }

    @GetMapping("/{id}")
    public CustomerDto getCustomer(@PathVariable("id") Long id) {
        return customerService.getCustomerById(id);
    }

    @PutMapping("/{id}")
    public void updateCustomer(@PathVariable("id") Long id, @RequestBody EditCustomerRequest request) {
        customerService.updateCustomer(id, request);
    }

    @DeleteMapping("/{id}")
    public void removeCustomer(@PathVariable("id") Long id) {
        customerService.removeCustomer(id);
    }

    @PostMapping("/allCustomers")
    public void addCustomers(List<CustomerDto> customersToAdd) {
        customerService.addCustomers(customersToAdd);
    }
}
