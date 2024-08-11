package pl.dare.carsharing.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.dare.carsharing.model.AddCarRequest;
import pl.dare.carsharing.model.CarDto;
import pl.dare.carsharing.model.CarsResponse;
import pl.dare.carsharing.model.EditCarRequest;
import pl.dare.carsharing.service.CarService;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

@RestController
@RequestMapping("cars")
public class CarResource {
    @Autowired
    private CarService service;

    @GetMapping
    public CarsResponse getCars() {
        CarsResponse carsResponse = new CarsResponse();
        carsResponse.setCars(service.getCars());
        return carsResponse;
    }

    @PostMapping
    public void addCar(@RequestBody AddCarRequest request) {
        service.addCar(request);
    }

    @GetMapping("/{id}")
    public CarDto getCarBy(@PathVariable("id") Long id) {
        return service.getCarById(id);
    }

    @PutMapping("/{id}")
    public void updateCar(@PathVariable("id") Long id, @RequestBody EditCarRequest request) {
        service.updateCar(id, request);
    }

    @DeleteMapping("/{id}")
    public void removeCar(@PathVariable("id") Long id) {
        service.removeCar(id);
    }

    @PostMapping("/all")
    public void addCars(List<CarDto> carsToAdd) {
        service.addCars(carsToAdd);
    }
}
