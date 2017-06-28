package pl.edu.agh.handy.offers.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.agh.handy.offers.model.User;
import pl.edu.agh.handy.offers.repository.UserRepository;
import pl.edu.agh.handy.offers.repository.UserRoleRepository;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);

        if (null == user) {
            throw new UsernameNotFoundException("No user present with username: " + username);
        }

        List<String> roles = userRoleRepository.findUserRoleByUserName(username);

        return new CustomUserDetails(user, roles);
    }
}
