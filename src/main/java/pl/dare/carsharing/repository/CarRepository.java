package pl.dare.carsharing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dare.carsharing.jpa.Car;


@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    //List<Car> findAll(); //SELECT * FROM CAR; To istnieje automatycznie
    void deleteById(Long carId);

}
