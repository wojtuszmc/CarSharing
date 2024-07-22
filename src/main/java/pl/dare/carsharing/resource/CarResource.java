package pl.dare.carsharing.resource;

import org.springframework.web.bind.annotation.*;
import pl.dare.carsharing.dto.CarDto;
import pl.dare.carsharing.service.CarService;

import java.util.List;

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
}
