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

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    public ReservationsResponse getReservations(
            @RequestParam(required = false) Long carId,
            @RequestParam(required = false) ZonedDateTime startDateFrom,
            @RequestParam(required = false) ZonedDateTime startDateTo,
            @RequestParam(defaultValue = "Europe/Warsaw") String timeZone) {

        ZoneId zoneId = ZoneId.of(timeZone);

        Instant startInstant = startDateFrom != null ? startDateFrom.toInstant() : null;
        Instant endInstant = startDateTo != null ? startDateTo.toInstant() : null;


        ReservationsResponse reservationsResponse = new ReservationsResponse();
        if (carId != null && startInstant != null && endInstant != null) {
            reservationsResponse.setReservations(reservationService
                    .getReservationsByCarAndDateRange(carId, startInstant, endInstant));
        } else if (carId != null) {
            reservationsResponse.setReservations(reservationService.getReservationsByCarId(carId));
        } else if (startDateFrom != null && endInstant != null) {
            reservationsResponse.setReservations(reservationService.getReservationsByDateRange(startInstant, endInstant));
        } else {
            reservationsResponse.setReservations(reservationService.getReservations());
        }
        return reservationsResponse;
    }

    @PostMapping
    public void addReservation(@RequestBody AddReservationRequest request) {
        reservationService.addReservation(request);
    }

    @DeleteMapping
    public void removeAll() {
        reservationService.removeReservations();
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
