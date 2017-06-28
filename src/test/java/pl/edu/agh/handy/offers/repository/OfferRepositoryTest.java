package pl.edu.agh.handy.offers.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.agh.handy.offers.model.Offer;
import pl.edu.agh.handy.offers.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Repository tests use H2 embedded database.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class OfferRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        User user1 = new User("John", 1, "Smith");
        User user2 = new User("Alex", 1, "Wallet");
        testEntityManager.persist(user1);
        testEntityManager.persist(user2);

        Offer offer = new Offer();
        offer.setTitle("Fryzjer damski");
        offer.setContent("Fryzjerka po szkole fryzjerskiej chetnie podejmie sie wykonania slubnej fryzury w ramach" +
                "wlasnego samodoskonalenia.");
        offer.setStartDate(LocalDateTime.of(2017, 03, 13, 12, 0));
        offer.setEndDate(LocalDateTime.of(2017, 03, 13, 12, 0));
        offer.setUser(user1);

        Offer offer2 = new Offer();
        offer2.setTitle("Elektryk amator");
        offer2.setContent("Elektryk amator z pasja do masterkowania chetnie wykona instalacje elektryczna bez " +
                "zezwolenia na odpowiedzialnosc osoby zlecajacej.");
        offer.setStartDate(LocalDateTime.of(2017, 03, 16, 12, 0));
        offer.setEndDate(LocalDateTime.of(2017, 06, 13, 12, 0));
        offer.setUser(user2);

        testEntityManager.persist(offer);
        testEntityManager.persist(offer2);
    }

    @Test
    public void findAllTest() throws Exception {
        List<Offer> offers = offerRepository.findAll();

        assertThat(offers.get(0).getTitle(), is("Fryzjer damski"));
        assertThat(offers.get(1).getTitle(), is("Elektryk amator"));
    }
}