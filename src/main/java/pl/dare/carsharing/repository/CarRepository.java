package pl.dare.carsharing.repository;

import pl.dare.carsharing.jpa.Car;

import java.util.ArrayList;
import java.util.List;

public class CarRepository {
    private List<Car> carDatabse = new ArrayList<>();

    public List<Car> getAllCars() {
        return carDatabse;
    }

    public void addCar(Car car) {
        carDatabse.add(car);
    }

    public void removeCar(int id) {
        carDatabse.remove(id - 1);
    }
}
