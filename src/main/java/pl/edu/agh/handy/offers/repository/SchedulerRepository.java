package pl.edu.agh.handy.offers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.handy.offers.model.Scheduler;

/**
 * Created by psk on 04.06.17.
 */
@Repository
public interface SchedulerRepository extends JpaRepository<Scheduler, Long> {
}
