package pl.edu.agh.handy.offers.converter;

import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;
import pl.edu.agh.handy.offers.dto.UserDto;
import pl.edu.agh.handy.offers.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter implements ModelConverter<UserDto, User> {

    private static final String DELIMITER = ", ";

    /**
     * Set user dto fields.
     * Properties enabled/banned -> true or false send to client.
     * @param model
     * @return dto
     */
    @Override
    public UserDto modelToDto(User model) {
        UserDto userDto = new UserDto();
        userDto.setId(String.valueOf(model.getId()));
        userDto.setEmail(model.getEmail());
        userDto.setFirstName(model.getFirstName());
        userDto.setLastName(model.getLastName());
        userDto.setUserName(model.getUserName());
        userDto.setEnabled(model.getEnabled() == 0 ? "0" : "1");
        userDto.setBanned(model.getBanned() == 0 ? "0" : "1");

        if (!model.getRoles().isEmpty()) {
            List<String> roles = model.getRoles()
                    .stream()
                    .map(role -> role.getRole())
                    .collect(Collectors.toList());
            userDto.setRoles(roles);

            String rolesString = String.join(DELIMITER, roles);
            userDto.setJoinedRoles(rolesString);
        }

        return userDto;
    }

    /**
     * Set user model fields.
     * Properties enabled/banned -> 0 or 1 send from client.
     * @param dto
     * @return model
     */
    @Override
    public User dtoToModel(UserDto dto) {
        User user = new User();
        if (dto.getId() != null) {
            user.setId(Long.valueOf(dto.getId()));
        }
        user.setUserName(StringUtils.isEmpty(dto.getUserName()) ? null : dto.getUserName());
        user.setPassword(StringUtils.isEmpty(dto.getPassword()) ? null : dto.getPassword());
        user.setEmail(StringUtils.isEmpty(dto.getEmail()) ? null : dto.getEmail());
        user.setFirstName(StringUtils.isEmpty(dto.getFirstName()) ? null : dto.getFirstName());
        user.setLastName(StringUtils.isEmpty(dto.getLastName()) ? null : dto.getLastName());
        user.setEnabled(dto.getEnabled() == null ? 0 : 1);
        user.setBanned(dto.getBanned() == null ? 0 : 1);
        return user;
    }
}
