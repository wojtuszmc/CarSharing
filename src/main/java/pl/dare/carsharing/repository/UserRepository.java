package pl.dare.carsharing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dare.carsharing.jpa.AppUser;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
}
