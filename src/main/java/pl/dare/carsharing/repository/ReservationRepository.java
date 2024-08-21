package pl.dare.carsharing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.dare.carsharing.jpa.Car;
import pl.dare.carsharing.jpa.Customer;
import pl.dare.carsharing.jpa.Reservation;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByCarAndCreatedAtAfter(Car car, LocalDateTime createdAt);

    boolean existsByCustomerAndCreatedAtAfter(Customer customer, LocalDateTime createdAt);

    void deleteById(Long reservationId);

    @Query("SELECT r FROM Reservation r WHERE r.car.id = :carId")
    List<Reservation> findReservationsByCarId(@Param("carId") Long carId);

}
