package pl.edu.agh.handy.offers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.handy.offers.model.Address;

/**
 * Created by psk on 04.05.17.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
