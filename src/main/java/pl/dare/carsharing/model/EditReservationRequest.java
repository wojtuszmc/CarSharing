package pl.dare.carsharing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class EditReservationRequest {

    private Long carId;

    private Long customerId;
}
