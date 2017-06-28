package pl.edu.agh.handy.offers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.agh.handy.offers.model.Category;
import pl.edu.agh.handy.offers.model.Offer;
import pl.edu.agh.handy.offers.model.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data access object for Offer entities.
 */
@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findByUser(User user);

    @Query(value = "select o from Offer o where o.id in :ids")
    List<Offer> findByIdIn(@Param("ids") List<Long> ids);

    List<Offer> findByReported(int reported);

    List<Offer> findByCategory(Category category);

    List<Offer> findByTitleContainingOrContentContaining(String title, String content);

    List<Offer> findByStartDateGreaterThan(LocalDateTime start);

    List<Offer> findByEndDateLessThan(LocalDateTime end);

    List<Offer> findByCategoryAndContentContaining(Category category, String content);

    List<Offer> findByContentContainingAndStartDateGreaterThan(String content, LocalDateTime start);

    List<Offer> findByContentContainingAndEndDateLessThan(String content, LocalDateTime end);

    List<Offer> findByContentContainingAndStartDateGreaterThanAndEndDateLessThan(String content,
                                                                                 LocalDateTime start, LocalDateTime end);
}
