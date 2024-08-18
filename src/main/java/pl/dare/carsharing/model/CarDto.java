package pl.dare.carsharing.model;

import lombok.*;

// Klasa na API, data transfer object czyli obiekt do komunikacji pomiedzy klientem, a serwerem.
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class CarDto {

    private String regNumber;
    private String model;
    private Long id;

}
