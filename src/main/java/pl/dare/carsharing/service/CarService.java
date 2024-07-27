package pl.dare.carsharing.service;

import pl.dare.carsharing.jpa.Car;
import pl.dare.carsharing.model.AddCarRequest;
import pl.dare.carsharing.model.CarDto;
import pl.dare.carsharing.model.EditCarRequest;
import pl.dare.carsharing.repository.CarRepository;

import java.util.ArrayList;
import java.util.List;

public class CarService {
    private CarRepository carRepository = new CarRepository();

    public void addCar(AddCarRequest request) {
        Car car = new Car();
        car.setId(request.getId());
        car.setModel(request.getModel());
        car.setRegNumber(request.getRegNumber());
        carRepository.addCar(car);
    }

    public List<CarDto> getCars() {
        List<CarDto> carsDto = new ArrayList<>();
        List<Car> cars = carRepository.getAllCars();
        for (Car car: cars) {
            CarDto carDto = new CarDto();
            carDto.setId(car.getId());
            carDto.setRegNumber(car.getRegNumber());
            carDto.setModel(car.getModel()); // Mapowanie jpa na dto
            carsDto.add(carDto);
        }
        return carsDto;
    }

    public void addCars(List<CarDto> carsToAdd) {
        getCars().addAll(carsToAdd);
    }

    public CarDto getCarById(int id) {
        for (CarDto carDto: getCars()) {
            if (carDto.getId() == id) {
                return carDto;
            }
        }
        return null;
    }

    public void removeCar(int id) {
        carRepository.removeCar(id);
    }

    public void updateCar(int id, EditCarRequest request) {
        removeCar(id);
        Car car = new Car();
        car.setRegNumber(request.getRegNumber());
        car.setModel(request.getModel());
        car.setId(id);
        carRepository.addCar(car);
    }
}
