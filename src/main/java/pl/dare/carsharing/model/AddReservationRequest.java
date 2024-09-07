package pl.dare.carsharing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class AddReservationRequest {

    private Long carId;
    private Long customerId;
    private ZonedDateTime startAt;
    private ZonedDateTime endAt;

}
