package pl.edu.agh.handy.offers.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Class that represent appointment.
 *
 * Appointment objects are used in Scheduler list
 * and in Reservation object.
 */
@Entity
public class Appointment extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    
    private LocalTime time;

    private int jsonObjectKey;

    public Appointment() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String print() {
        return date.toString() + " " + time.toString();
    }

    public void setJsonObjectKey(int jsonObjectKey) {
        this.jsonObjectKey = jsonObjectKey;
    }

    public int getJsonObjectKey() {
        return jsonObjectKey;
    }
}
