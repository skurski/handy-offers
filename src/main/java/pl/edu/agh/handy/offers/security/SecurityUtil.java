package pl.edu.agh.handy.offers.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Common security methods.
 */
public final class SecurityUtil {

    public static String passwordGenerator(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
