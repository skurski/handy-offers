package pl.edu.agh.handy.offers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.handy.offers.model.Communication;

/**
 * Created by psk on 04.05.17.
 */
@Repository
public interface CommunicationRepository extends JpaRepository<Communication, Long> {
}
