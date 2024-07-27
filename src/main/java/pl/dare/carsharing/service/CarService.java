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

    public CarDto getCarByModel(String model) {
        for (CarDto carDto: getCars()) {
            if (model.equalsIgnoreCase(carDto.getModel())) {
                return carDto;
            }
        }
        return null;
    }

    public CarDto getCarByRegNumber(String regNumber) {
        for (CarDto carDto: getCars()) {
            if (regNumber.equalsIgnoreCase(carDto.getRegNumber())) {
                return carDto;
            }
        }
        return null;
    }

    public void removeCarByRegNumber(String regNumber) {
        CarDto carDto = getCarByRegNumber(regNumber);
        getCars().remove(carDto);
    }

    public void addCars(List<CarDto> carsToAdd) {
        getCars().addAll(carsToAdd);
    }

    public void getCarModel(String regNumber, String newModel) {
        CarDto carDto = getCarByRegNumber(regNumber);
        carDto.setModel(newModel);
    }
}
