package pl.dare.carsharing.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.dare.carsharing.model.AddReservationRequest;
import pl.dare.carsharing.model.EditReservationRequest;
import pl.dare.carsharing.model.ReservationDto;
import pl.dare.carsharing.model.ReservationsResponse;
import pl.dare.carsharing.service.CarService;
import pl.dare.carsharing.service.CustomerService;
import pl.dare.carsharing.service.ReservationService;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

@RestController
@RequestMapping("reservations")
public class ReservationResource {
    @Autowired
    ReservationService reservationService;

    @Autowired
    CarService carService;

    @Autowired
    CustomerService customerService;

    @GetMapping
    public ReservationsResponse getReservations() {
        ReservationsResponse reservationsResponse = new ReservationsResponse();
        reservationsResponse.setReservations(reservationService.getReservations());
        return reservationsResponse;
    }

    @PostMapping
    public void addReservation(@RequestBody AddReservationRequest request) {
        reservationService.addReservation(request);
    }

    @GetMapping("/{id}")
    public ReservationDto getReservationById(@PathVariable("id") Long id) {
        return reservationService.getReservationById(id);
    }

    @PutMapping("/{id}")
    public void updateReservation(@PathVariable("id") Long id, @RequestBody EditReservationRequest request) {
        reservationService.updateReservation(id, request);
    }

    @DeleteMapping("/{id}")
    public void removeReservation(@PathVariable("id") Long id) {
        reservationService.removeReservation(id);
    }

    @PostMapping("/all")
    public void addReservations(List<ReservationDto> reservationsToAdd) {
        reservationService.addReservations(reservationsToAdd);
    }
}
