package pl.dare.carsharing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.dare.carsharing.jpa.Car;
import pl.dare.carsharing.jpa.Customer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ReservationDto {

    private Long id;
    private CarDto carDto;
    private CustomerDto customerDto;
}
