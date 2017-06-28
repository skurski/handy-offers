package pl.edu.agh.handy.offers.dto;

/**
 * Created by psk on 07.05.17.
 */
public class SmartSearchDto {

    private String keywords;

    private String latitude;

    private String longitude;

    private int radiusValue;

    private long categoryId;

    private String fromDate;

    private String toDate;

    public SmartSearchDto() {}

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
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

    public int getRadiusValue() {
        return radiusValue;
    }

    public void setRadiusValue(int radiusValue) {
        this.radiusValue = radiusValue;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    @Override
    public String toString() {
        return "SmartSearchDto{" +
                "keywords='" + keywords + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", radiusValue=" + radiusValue +
                ", categoryId=" + categoryId +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                '}';
    }
}
