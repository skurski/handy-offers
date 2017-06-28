package pl.edu.agh.handy.offers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.handy.offers.model.Category;

/**
 * Category data access object.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findById(long id);
}
