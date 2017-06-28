package pl.edu.agh.handy.offers.model;


import javax.persistence.*;

/**
 * Keeps data about user reservation.
 *
 * After reservation appointment is removed from Scheduler
 * appointments list and moved to Reservation object.
 *
 * One user == one reservation == one offer.
 * System does not allowed multiple reservation
 * from one user for one offer!
 */
@Entity
@Table(name="reservation")
public class Reservation extends BaseModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    /** Date, time of the appointment that is reserved */
    @OneToOne
    private Appointment appointment;

    @ManyToOne
    private Offer offer;
    
    public Reservation() {}

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

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", user=" + user +
                ", appointment=" + appointment +
                ", offer=" + offer +
                '}';
    }
}
