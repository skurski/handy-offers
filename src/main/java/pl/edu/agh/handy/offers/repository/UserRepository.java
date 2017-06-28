package pl.edu.agh.handy.offers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.handy.offers.model.User;

/**
 * Data access object for User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String username);

    Long countByEmail(String email);
}