package pl.dare.carsharing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ReservationDto {

    private Long id;
    private CarDto carDto;
    private CustomerDto customerDto;
    private Instant createdAt;
    private Instant endLagAt;
    private Instant startAt;
    private Instant endAt;
}
