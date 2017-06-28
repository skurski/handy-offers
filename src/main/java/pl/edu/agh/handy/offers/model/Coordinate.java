package pl.edu.agh.handy.offers.model;

import javax.persistence.*;

/**
 * Class representing offer localization.
 * <p>
 * It uses latitude and longitude coordinates.
 */
@NamedNativeQueries({
    @NamedNativeQuery(
        name = "findCoordinateInRadius",
        query = "SELECT offer.id as offer_id, coordinate.id, coordinate.latitude, coordinate.longitude,\n" +
                "ACOS( SIN( RADIANS( latitude ) ) * SIN( RADIANS( :centerLat ) ) + COS( RADIANS( latitude ) )\n" +
                "* COS( RADIANS( :centerLat )) * COS( RADIANS( longitude ) - RADIANS( :centerLng )) ) * 6380 AS distance\n" +
                "FROM coordinate\n" +
                "INNER JOIN offer on offer.coordinate_id = coordinate.id\n" +
                "WHERE\n" +
                "ACOS( SIN( RADIANS( latitude ) ) * SIN( RADIANS( :centerLat ) ) + COS( RADIANS( latitude ) )\n" +
                "* COS( RADIANS( :centerLat )) * COS( RADIANS( longitude ) - RADIANS( :centerLng )) ) * 6380 < :radius\n" +
                "AND offer.category_id = :categoryId\n" +
                "AND offer.title LIKE CONCAT('%', ?, '%')\n" +
                "ORDER BY distance",
        resultClass = Coordinate.class
    )
})
@Entity
public class Coordinate extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String latitude;

    private String longitude;

    @Transient
    private double distance;

    @Transient
    private Long offerId;

    public Coordinate() {
    }

    public Coordinate(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }
}
