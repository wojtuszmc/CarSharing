package pl.dare.carsharing.service;

import pl.dare.carsharing.dto.CarDto;

import java.util.ArrayList;
import java.util.List;

public class CarService {
    private List<CarDto> carList = new ArrayList<>();

    public void addCar(CarDto car) {
        carList.add(car);
    }

    public List<CarDto> getCars() {
        return carList;
    }
}
