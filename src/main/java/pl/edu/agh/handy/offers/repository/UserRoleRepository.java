package pl.edu.agh.handy.offers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.agh.handy.offers.model.UserRole;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("select r.role from UserRole r, User u where u.userName=?1 and r.user.id=u.id")
    List<String> findUserRoleByUserName(String userName);

    @Query("select r from UserRole r where r.user.id=?1")
    List<UserRole> findUserRoleByUserId(Long userId);
}
