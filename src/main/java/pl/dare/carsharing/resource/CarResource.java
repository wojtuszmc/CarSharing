package pl.dare.carsharing.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.*;
import pl.dare.carsharing.dto.CarDto;
import pl.dare.carsharing.service.CarService;

import java.util.List;
import java.util.Optional;

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

    @PostMapping
    public void addCar(@RequestBody CarDto car) {
        service.addCar(car);
    }

    @GetMapping
    public CarDto getCarByModel(String model) {
        for (CarDto carDto: getCars()) {
            if (model.equalsIgnoreCase(carDto.getModel())) {
                return carDto;
            }
        }
        return null;
    }

    @GetMapping
    public CarDto getCarByRegNumber(String regNumber) {
        for (CarDto carDto: getCars()) {
            if (regNumber.equalsIgnoreCase(carDto.getRegNumber())) {
                return carDto;
            }
        }
        return null;
    }

    @PutMapping
    public void updateCarModel(String regNumber, String newModel) {
        CarDto carDto = getCarByRegNumber(regNumber);
            carDto.setModel(newModel);
    }

    @DeleteMapping
    public void removeCarByRegNumber(String regNumber) {
        CarDto carDto = getCarByRegNumber(regNumber);
        getCars().remove(carDto);
    }

    @PostMapping
    public void addCars(List<CarDto> carsToAdd) {
        getCars().addAll(carsToAdd);
    }
}
