package pl.dare.carsharing.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.dare.carsharing.model.AddReservationRequest;
import pl.dare.carsharing.model.EditReservationRequest;
import pl.dare.carsharing.model.ReservationDto;
import pl.dare.carsharing.model.ReservationsResponse;
import pl.dare.carsharing.service.CarService;
import pl.dare.carsharing.service.CustomerService;
import pl.dare.carsharing.service.ReservationService;

import java.time.DateTimeException;
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

    @Value("${app.default-timezone:Europe/Warsaw}")
    private String defaultTimeZone;

    @GetMapping
    public ReservationsResponse getReservations(
            @RequestParam(required = false) Long carId,
            @RequestParam(required = false) ZonedDateTime startDateFrom,
            @RequestParam(required = false) ZonedDateTime startDateTo,
            @RequestHeader(value = "X-Time-Zone", required = false) String timeZone) {

        timeZone = validateAndGetTimeZone(timeZone);
        ZoneId zoneId = ZoneId.of(timeZone);


        Instant startInstant = startDateFrom != null ? startDateFrom.toInstant() : null;
        Instant endInstant = startDateTo != null ? startDateTo.toInstant() : null;


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
    public ResponseEntity<Void> addReservation(@RequestBody AddReservationRequest request,
                        @RequestHeader(value = "X-Time-Zone", required = false) String timeZone) {
        timeZone = validateAndGetTimeZone(timeZone);
        reservationService.addReservation(request, timeZone);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeAll() {
        reservationService.removeReservations();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ReservationDto getReservationById(@PathVariable("id") Long id,
        @RequestHeader(defaultValue = "X-Time-Zone", required = false) String timeZone) {
        timeZone = validateAndGetTimeZone(timeZone);
        return reservationService.getReservationById(id, timeZone);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateReservation(@PathVariable("id") Long id,
                                  @RequestBody EditReservationRequest request,
                                  @RequestHeader(value = "X-Time-Zone", required = false) String timeZone) {
        timeZone = validateAndGetTimeZone(timeZone);
        reservationService.updateReservation(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeReservation(@PathVariable("id") Long id) {
        reservationService.removeReservation(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/all")
    public ResponseEntity<Void> addReservations(@RequestBody List<ReservationDto> reservationsToAdd,
                                @RequestHeader(value = "X-Time_Zone", required = false) String timeZone) {
        timeZone = validateAndGetTimeZone(timeZone);
        reservationService.addReservations(reservationsToAdd, timeZone);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private String validateAndGetTimeZone(String timeZone) {
        if (timeZone == null || timeZone.isEmpty()) {
            return defaultTimeZone;
        }

        try {
            ZoneId.of(timeZone);
            return timeZone;
        } catch (DateTimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid timezone: " + timeZone);
        }
    }
}
