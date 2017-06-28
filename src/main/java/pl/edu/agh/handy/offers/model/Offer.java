package pl.edu.agh.handy.offers.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity with all information about offer.
 */
@Entity
@Table(name="offer")
public class Offer extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    /**
     * User with rating value smaller than
     * offer threshold value cannot make reservation.
     */
    private int threshold = 50;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;

    @OneToOne
    private Coordinate coordinate;

    @OneToOne
    private Scheduler scheduler;

    @OneToOne
    private Communication communication;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    private int reported = 0;

    private int banned = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Communication getCommunication() {
        return communication;
    }

    public void setCommunication(Communication communication) {
        this.communication = communication;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public int getReported() {
        return reported;
    }

    public void setReported(int reported) {
        this.reported = reported;
    }

    public int getBanned() {
        return banned;
    }

    public void setBanned(int banned) {
        this.banned = banned;
    }


    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", threshold=" + threshold +
                ", user=" + user +
                ", category=" + category +
                ", coordinate=" + coordinate +
                ", scheduler=" + scheduler +
                ", communication=" + communication +
                ", reservations=" + reservations +
                ", reported=" + reported +
                ", banned=" + banned +
                '}';
    }
}
