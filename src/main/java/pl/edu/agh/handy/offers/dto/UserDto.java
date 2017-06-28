package pl.edu.agh.handy.offers.dto;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

public class UserDto extends BaseDto {

    @NotBlank(message = "{error.required}")
    @Email(message = "{error.email}")
    private String email;

    @Length(min = 12, message = "{error.length}")
    private String password;

    @NotBlank(message = "{error.required}")
    private String firstName;

    @NotBlank(message = "{error.required}")
    private String lastName;

    @NotBlank(message = "{error.required}")
    private String userName;

    private List<String> roles;

    private String joinedRoles;

    private String enabled;

    private String banned;

    public UserDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getJoinedRoles() {
        return joinedRoles;
    }

    public void setJoinedRoles(String joinedRoles) {
        this.joinedRoles = joinedRoles;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getBanned() {
        return banned;
    }

    public void setBanned(String banned) {
        this.banned = banned;
    }
}
