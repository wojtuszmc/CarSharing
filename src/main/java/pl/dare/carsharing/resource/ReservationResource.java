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

        Instant startInstant = startDateFrom != null ? startDateFrom.withZoneSameInstant(zoneId).toInstant() : null;
        Instant endInstant = startDateTo != null ? startDateTo.withZoneSameInstant(zoneId).toInstant() : null;


        ReservationsResponse reservationsResponse = new ReservationsResponse();
        if (carId != null && startInstant != null && endInstant != null) {
            reservationsResponse.setReservations(reservationService
                    .getReservationsByCarAndDateRange(carId, startInstant, endInstant, timeZone));
        } else if (carId != null) {
            reservationsResponse.setReservations(reservationService.getReservationsByCarId(carId, timeZone));
        } else if (startInstant != null && endInstant != null) {
            reservationsResponse.setReservations(reservationService.getReservationsByDateRange(startInstant, endInstant, timeZone));
        } else {
            reservationsResponse.setReservations(reservationService.getReservations(timeZone));
        }
        return reservationsResponse;
    }

    @PostMapping
    public void addReservation(@RequestBody AddReservationRequest request,
            @RequestParam(defaultValue = "Europe/Warsaw") String timeZone) {
        reservationService.addReservation(request, timeZone);
    }

    @DeleteMapping
    public void removeAll() {
        reservationService.removeReservations();
    }

    @GetMapping("/{id}")
    public ReservationDto getReservationById(@PathVariable("id") Long id,
        @RequestParam(defaultValue = "Europe/Warsaw") String timeZone) {
        return reservationService.getReservationById(id, timeZone);
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
    public void addReservations(List<ReservationDto> reservationsToAdd,
                                @RequestParam(defaultValue = "Europe/Warsaw") String timeZone) {
        reservationService.addReservations(reservationsToAdd, timeZone);
    }
}
