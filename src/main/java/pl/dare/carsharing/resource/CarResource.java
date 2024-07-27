package pl.dare.carsharing.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.*;
import pl.dare.carsharing.dto.CarDto;
import pl.dare.carsharing.service.CarService;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

@RestController
@RequestMapping("cars")
public class CarResource {

    private CarService service = new CarService();

    @GetMapping
    public List<CarDto> getCars() {
        return service.getCars();
    }

    @PostMapping("/detail")
    public void addCar(@RequestBody CarDto car) {
        service.addCar(car);
    }

    @GetMapping("/details")
    public CarDto getCarByModel(String model) {
        return service.getCarByModel(model);
    }

    @GetMapping("/dashboard/admin/list")
    public CarDto getCarByRegNumber(String regNumber) {
        return service.getCarByRegNumber(regNumber);
    }

    @PutMapping
    public void updateCarModel(String regNumber, String newModel) {
        service.getCarModel(regNumber, newModel);
    }

    @DeleteMapping
    public void removeCarByRegNumber(String regNumber) {
        service.removeCarByRegNumber(regNumber);
    }

    @PostMapping
    public void addCars(List<CarDto> carsToAdd) {
        service.addCars(carsToAdd);
    }
}
