package pl.edu.agh.handy.offers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import pl.edu.agh.handy.offers.converter.UserConverter;
import pl.edu.agh.handy.offers.dto.UserDto;
import pl.edu.agh.handy.offers.exception.UserAlreadyRegistered;
import pl.edu.agh.handy.offers.exception.EntityNotFound;
import pl.edu.agh.handy.offers.model.*;
import pl.edu.agh.handy.offers.repository.*;
import pl.edu.agh.handy.offers.security.Roles;
import pl.edu.agh.handy.offers.security.SecurityUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService implements DaoService<User, UserDto> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public User create(UserDto userDto) throws UserAlreadyRegistered {
        if (userRepository.countByEmail(userDto.getEmail()) > 0) {
            throw new UserAlreadyRegistered("User already registered!");
        }
        User user = userConverter.dtoToModel(userDto);
        user.setEnabled(1);
        user.setPassword(SecurityUtil.passwordGenerator(userDto.getPassword()));

        Rating rating = new Rating(user);
        ratingRepository.save(rating);
        user.setRating(rating);
        userRepository.save(user);

        UserRole userUserRole = new UserRole(user, Roles.ROLE_USER.toString());
        userRoleRepository.save(userUserRole);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(Roles.ROLE_USER.toString()));
        Authentication auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);

        return user;
    }

    @Override
    public void delete(UserDto userDto) throws EntityNotFound {
        User user = userConverter.dtoToModel(userDto);

        List<Offer> offers = offerRepository.findByUser(user);
        offerRepository.delete(offers);

        List<Reservation> reservations = reservationRepository.findByUser(user);
        reservationRepository.delete(reservations);

        Rating rating = ratingRepository.findByUser(user);
        ratingRepository.delete(rating);

        List<UserRole> userRoleList = userRoleRepository.findUserRoleByUserId(user.getId());
        userRoleRepository.delete(userRoleList);

        userRepository.delete(user.getId());
    }

    @Override
    public List<UserDto> findAll() {
        List<UserDto> users = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            users.add(userConverter.modelToDto(user));
        }

        return users;    }

    @Override
    public User update(UserDto userDto) throws EntityNotFound {
        User user = userRepository.findOne(Long.valueOf(userDto.getId()));
        user.setEmail(StringUtils.isEmpty(userDto.getEmail()) ? null : userDto.getEmail());
        user.setFirstName(StringUtils.isEmpty(userDto.getFirstName()) ? null : userDto.getFirstName());
        user.setLastName(StringUtils.isEmpty(userDto.getLastName()) ? null : userDto.getLastName());
        user.setUserName(StringUtils.isEmpty(userDto.getUserName()) ? null : userDto.getUserName());

        userRepository.save(user);
        return user;
    }

    @Override
    public UserDto findById(long id) {
        return userConverter.modelToDto(userRepository.findOne(id));
    }
}
