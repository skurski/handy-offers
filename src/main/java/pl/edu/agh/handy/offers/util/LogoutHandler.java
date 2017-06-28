package pl.edu.agh.handy.offers.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

public class LogoutHandler {

    public static void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();

        Authentication auth = new AnonymousAuthenticationToken( "anonymousUser", "anonymousUser",
                createAuthorityList( "ROLE_ANONYMOUS" ) );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
