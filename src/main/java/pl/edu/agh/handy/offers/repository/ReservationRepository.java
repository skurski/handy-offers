package pl.edu.agh.handy.offers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.handy.offers.model.Offer;
import pl.edu.agh.handy.offers.model.Reservation;
import pl.edu.agh.handy.offers.model.User;

import java.util.List;

/**
 * Reservation repository.
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUser(User user);
}
