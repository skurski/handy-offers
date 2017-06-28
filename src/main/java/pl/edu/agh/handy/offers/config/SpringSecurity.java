package pl.edu.agh.handy.offers.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static pl.edu.agh.handy.offers.common.Links.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    private static final String PARAMETER = "username";
    private static final String PASSWORD = "password";
    private static final String LOGIN_LOGOUT = "/login?logout";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(Url.ADMIN).access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
//                .anyRequest().authenticated()
                .and()
                    .formLogin().loginPage(Url.LOGIN)
                    .usernameParameter(PARAMETER).passwordParameter(PASSWORD)
                .and()
                    .logout().logoutSuccessUrl(LOGIN_LOGOUT)
                    .and()
                    .exceptionHandling().accessDeniedPage(Url.FORBIDDEN)
                .and()
                    .csrf();

//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
    }

    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
//    }
}