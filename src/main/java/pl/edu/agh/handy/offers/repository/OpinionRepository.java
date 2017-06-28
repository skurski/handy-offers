package pl.edu.agh.handy.offers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.handy.offers.model.Opinion;

/**
 * Created by psk on 16.06.17.
 */
@Repository
public interface OpinionRepository extends JpaRepository<Opinion, Long> {
}
