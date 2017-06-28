package pl.edu.agh.handy.offers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.handy.offers.model.Rating;
import pl.edu.agh.handy.offers.model.User;

/**
 * Created by psk on 16.06.17.
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    Rating findByUser(User user);
}