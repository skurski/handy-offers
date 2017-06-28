package pl.edu.agh.handy.offers.model;

import javax.persistence.*;

@Entity
@Table(name="user_role")
public class UserRole extends BaseModel {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Column(name = "role")
    private String role;

    public UserRole() {}

    public UserRole(User user, String role) {
        this.user = user;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
