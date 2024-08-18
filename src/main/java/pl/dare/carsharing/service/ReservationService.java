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
        Car car = carRepository.findById(request.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid car ID"));

        Customer customer = customerRepository.findById(request.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid" +
                "customer ID"));

        Reservation reservation = new Reservation();
        reservation.setCar(car);
        reservation.setCustomer(customer);
        reservationRepository.save(reservation);
    }

    public List<ReservationDto> getReservations() {
        List<ReservationDto> reservationsDto = new ArrayList<>();
        List<Reservation> reservations = reservationRepository.findAll();

        for (Reservation reservation : reservations) {
            ReservationDto reservationDto = new ReservationDto();
            CarDto carDto = mapToCarDto(reservation.getCar());
            reservationDto.setCarDto(carDto);
            CustomerDto customerDto = mapToCustomerDto(reservation.getCustomer());
            reservationDto.setCustomerDto(customerDto);
            reservationsDto.add(reservationDto);
        }
        return reservationsDto;
    }

    private CarDto mapToCarDto(Car car) {
        CarDto carDto = new CarDto();
        carDto.setRegNumber(car.getRegNumber());
        carDto.setModel(car.getModel());
        return carDto;
    }

    private CustomerDto mapToCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(customer.getName());
        customerDto.setLastName(customer.getLastName());
        return customerDto;
    }

    private ReservationDto mapToReservationDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setCarDto(mapToCarDto(reservation.getCar()));
        reservationDto.setCustomerDto(mapToCustomerDto(reservation.getCustomer()));
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

    public void updateReservation(Long id, EditReservationRequest request) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Reservation" +
                " not found"));
        Customer customer = customerRepository.findById(request.getCustomerDto().getId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        Car car = carRepository.findById(request.getCarDto().getId())
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));

        reservation.setCustomer(customer);
        reservation.setCar(car);
        reservationRepository.save(reservation);
    }
}
