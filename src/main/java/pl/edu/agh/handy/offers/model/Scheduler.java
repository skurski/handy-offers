package pl.edu.agh.handy.offers.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Scheduler - calendar with free appointments.
 * Every offer has a scheduler with free appointments.
 * When user made a reservation appointment is removed from the list.
 *
 * Every schedule contains appointments for 7 days period.
 */
@Entity
public class Scheduler extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Appointment> appointments = new ArrayList<>();

    public Scheduler() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }

    @Override
    public String toString() {
        return "Scheduler{" +
                "id=" + id +
                ", appointments=" + appointments +
                '}';
    }
}
