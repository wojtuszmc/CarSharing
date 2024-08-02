package pl.dare.carsharing.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.*;
import pl.dare.carsharing.model.AddCarRequest;
import pl.dare.carsharing.model.AddCustomerRequest;
import pl.dare.carsharing.model.CustomerResponse;
import pl.dare.carsharing.service.CustomerService;

@AllArgsConstructor
@ToString
@NoArgsConstructor
@Data

@RestController
@RequestMapping("customers")

public class CustomerResource {

    private CustomerService customerService = new CustomerService();


    @GetMapping
    public CustomerResponse getCustomers() {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setCustomers(customerService.getCustomers());
        return customerResponse;
    }

    @PostMapping
    public void addCustomer(@RequestBody AddCustomerRequest request) {
        customerService.addCustomer(request);
    }
}
