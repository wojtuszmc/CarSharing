package pl.dare.carsharing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dare.carsharing.jpa.Car;
import pl.dare.carsharing.model.AddCarRequest;
import pl.dare.carsharing.model.CarDto;
import pl.dare.carsharing.model.EditCarRequest;
import pl.dare.carsharing.repository.CarRepository;

import java.util.ArrayList;
import java.util.List;
@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public void addCar(AddCarRequest request) {
        Car car = new Car();
        car.setModel(request.getModel());
        car.setRegNumber(request.getRegNumber());
        carRepository.save(car);
    }

    public List<CarDto> getCars() {
        List<CarDto> carsDto = new ArrayList<>();
        List<Car> cars = carRepository.findAll();
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

    public CarDto getCarById(Long id) {
        for (CarDto carDto: getCars()) {
            if (carDto.getId() == id) {
                return carDto;
            }
        }
        return null;
    } // zad. Select From Car where id = id. w CarRepository

    public void removeCar(Long id) {
        carRepository.deleteById(id);
    }

    public void updateCar(Long id, EditCarRequest request) {
        removeCar(id);
        Car car = new Car();
        car.setRegNumber(request.getRegNumber());
        car.setModel(request.getModel());
        carRepository.save(car); // Jebnij update w bazie, bez usuwania
    }
}
