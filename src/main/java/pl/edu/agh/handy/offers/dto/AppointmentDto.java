package pl.edu.agh.handy.offers.dto;

/**
 * Data transfer object for Appointment.
 */
public class AppointmentDto extends BaseDto {

    private String date;

    private String time;

    public AppointmentDto() {}

    public String print() {
        return date + " " + time;
    }
    
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "AppointmentDto{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}