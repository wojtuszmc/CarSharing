package pl.dare.carsharing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dare.carsharing.jpa.Car;
import pl.dare.carsharing.jpa.Customer;
import pl.dare.carsharing.jpa.Reservation;
import pl.dare.carsharing.model.*;
import pl.dare.carsharing.repository.CarRepository;
import pl.dare.carsharing.repository.CustomerRepository;
import pl.dare.carsharing.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public void addReservation(AddReservationRequest request) {
        Car car = carRepository.findById(request.getCarId()).orElseThrow(() -> new IllegalArgumentException("Invalid car ID"));

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threeMinutesAgo = LocalDateTime.now().minusMinutes(3);

        boolean carReserved = reservationRepository.existsByCarAndCreatedAtAfter(car, threeMinutesAgo);
        boolean customerReserved = reservationRepository.existsByCustomerAndCreatedAtAfter(customer, threeMinutesAgo);

        if (carReserved || customerReserved) {
            throw new IllegalArgumentException("The car or customer has an active reservation within the last 3 minutes.");
        }

        Reservation reservation = new Reservation();
        reservation.setCar(car);
        reservation.setCustomer(customer);
        reservation.setCreatedAt(now);
        reservation.setEndAt(now.plusMinutes(3));
        reservationRepository.save(reservation);
    }

    public List<ReservationDto> getReservations() {
        List<ReservationDto> reservationsDto = new ArrayList<>();
        List<Reservation> reservations = reservationRepository.findAll();

        for (Reservation reservation : reservations) {
            ReservationDto reservationDto = mapToReservationDto(reservation);
            reservationsDto.add(reservationDto);
        }
        return reservationsDto;
    }

    public List<ReservationDto> getReservationsByCarId(Long carId) {
        List<ReservationDto> reservationsDto = new ArrayList<>();
        List<Reservation> reservations = reservationRepository.findReservationsByCarId(carId);

        for (Reservation reservation : reservations) {
            ReservationDto reservationDto = mapToReservationDto(reservation);
            reservationsDto.add(reservationDto);
        }
        return reservationsDto;
    }

    private CarDto mapToCarDto(Car car) {
        CarDto carDto = new CarDto();
        carDto.setId(car.getId());
        carDto.setRegNumber(car.getRegNumber());
        carDto.setModel(car.getModel());
        return carDto;
    }

    private CustomerDto mapToCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setLastName(customer.getLastName());
        return customerDto;
    }

    private ReservationDto mapToReservationDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setCarDto(mapToCarDto(reservation.getCar()));
        reservationDto.setCustomerDto(mapToCustomerDto(reservation.getCustomer()));
        reservationDto.setCreatedAt(reservation.getCreatedAt());
        reservationDto.setEndAt(reservation.getEndAt());
        return reservationDto;
    }

    public void addReservations(List<ReservationDto> reservationsToAdd) {
        getReservations().addAll(reservationsToAdd);
    }

    public ReservationDto getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Reservation" +
                " not found"));
        ReservationDto reservationDto = mapToReservationDto(reservation);
        return reservationDto;
    }

    public void removeReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public void removeReservations() {
        reservationRepository.deleteAll();
    }

    public void updateReservation(Long id, EditReservationRequest request) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Reservation" +
                " not found"));
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        Car car = carRepository.findById(request.getCarId())
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));

        reservation.setCustomer(customer);
        reservation.setCar(car);
        reservationRepository.save(reservation);
    }
}
