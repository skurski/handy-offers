package pl.edu.agh.handy.offers.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

/**
 * Data transfer object for Offer.
 */
public class OfferDto extends BaseDto {

    @NotBlank(message = "{error.required}")
    private String title;

    @NotBlank(message = "{error.required}")
    private String content;

    @NotBlank(message = "{error.required}")
    private String startDate;

    @NotBlank(message = "{error.required}")
    private String endDate;

    @NotBlank(message = "{error.required}")
    @Email(message = "{error.email}")
    private String email;

    @NotBlank(message = "{error.required}")
    private String phone;

    private String city;

    private String postcode;

    @NotBlank(message = "{error.required}")
    private String street;

    @NotBlank(message = "{error.required}")
    private String number;

    private String categoryId;

    private String categoryName;

    private String latitude;

    private String longitude;

    private String distance;

    private String rating = "100";

    private List<String> opinions = new ArrayList<>();

    private String reported = "0";

    private String banned = "0";

    @NotBlank(message = "{error.required}")
    private String threshold = "50";

    /**
     * json object for JS scheduler
     */
    @NotBlank(message = "{error.scheduler.required}")
    private String scheduler;

    private String schedulerId;

    private String communicationId;

    private String coordinateId;
    
    private List<AppointmentDto> appointments;

    public OfferDto() {}

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getFullAddress() {
        return street + " " + number + ", " + city;
    }

    public String getScheduler() {
        return scheduler;
    }

    public void setScheduler(String scheduler) {
        this.scheduler = scheduler;
    }

    public List<AppointmentDto> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentDto> appointments) {
        this.appointments = appointments;
    }

    public String getSchedulerId() {
        return schedulerId;
    }

    public void setSchedulerId(String schedulerId) {
        this.schedulerId = schedulerId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<String> getOpinions() {
        return opinions;
    }

    public void setOpinions(List<String> opinions) {
        this.opinions = opinions;
    }

    public String getReported() {
        return reported;
    }

    public void setReported(String reported) {
        this.reported = reported;
    }

    public String getBanned() {
        return banned;
    }

    public void setBanned(String banned) {
        this.banned = banned;
    }

    public String getCommunicationId() {
        return communicationId;
    }

    public void setCommunicationId(String communicationId) {
        this.communicationId = communicationId;
    }

    public String getCoordinateId() {
        return coordinateId;
    }

    public void setCoordinateId(String coordinateId) {
        this.coordinateId = coordinateId;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }
}