package pl.edu.agh.handy.offers.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.agh.handy.offers.model.UserRole;
import pl.edu.agh.handy.offers.model.User;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRoleRepositoryTest {

    public static final String JOHN = "zombie";
    public static final String AGA = "aga";
    public static final String PETER = "peter";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Before
    public void setUp() throws Exception {
        User user1 = new User(JOHN, 1, "password");
        User user2 = new User(AGA, 1, "password");
        User user3 = new User(PETER, 1, "password");

        testEntityManager.persist(user1);
        testEntityManager.persist(user2);
        testEntityManager.persist(user3);

        UserRole userRole1 = new UserRole(user1, ROLE_USER);
        UserRole userRole2 = new UserRole(user2, ROLE_USER);
        UserRole userRole3 = new UserRole(user3, ROLE_ADMIN);

        testEntityManager.persist(userRole1);
        testEntityManager.persist(userRole2);
        testEntityManager.persist(userRole3);
    }

    @Test
    public void findAllTest() throws Exception {
        List<String> roles = userRoleRepository.findUserRoleByUserName(JOHN);
        assertThat(roles.get(0), is(ROLE_USER));

        List<String> roles2 = userRoleRepository.findUserRoleByUserName(AGA);
        assertThat(roles2.get(0), is(ROLE_USER));

        List<String> roles3 = userRoleRepository.findUserRoleByUserName(PETER);
        assertThat(roles3.get(0), is(ROLE_ADMIN));
    }
}
