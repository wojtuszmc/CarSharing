package pl.dare.carsharing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dare.carsharing.jpa.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    void deleteById(Long reservationId);
}
