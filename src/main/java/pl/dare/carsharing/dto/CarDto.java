package pl.dare.carsharing.dto;

import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class CarDto {

    private String regNumber;
    private String model;
    private int id;

}
