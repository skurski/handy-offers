package pl.edu.agh.handy.offers.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import pl.edu.agh.handy.offers.model.Coordinate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Finding all offers within a chosen distance.
 */
@Service
public class SmartSearchService {

    private final JdbcTemplate jdbcTemplate;

    private static final String LIMIT_NUMBER = "20";

    private static final String BY_LOCATION =
            "SELECT offer.id as offer_id, coordinate.id, coordinate.latitude, coordinate.longitude,\n" +
                    "ACOS( SIN( RADIANS( latitude ) ) * SIN( RADIANS( ? ) ) + COS( RADIANS( latitude ) )\n" +
                    "* COS( RADIANS( ? )) * COS( RADIANS( longitude ) - RADIANS( ? )) ) * 6380 AS distance\n" +
                    "FROM coordinate\n" +
                    "INNER JOIN offer on offer.coordinate_id = coordinate.id\n" +
                    "WHERE\n" +
                    "ACOS( SIN( RADIANS( latitude ) ) * SIN( RADIANS( ? ) ) + COS( RADIANS( latitude ) )\n" +
                    "* COS( RADIANS( ? )) * COS( RADIANS( longitude ) - RADIANS( ? )) ) * 6380 < ?\n" +
                    "ORDER BY distance\n" +
                    "LIMIT " + LIMIT_NUMBER;

    private static final String BY_COORDINATE_CATEGORY_KEYWORDS =
            "SELECT offer.id as offer_id, coordinate.id, coordinate.latitude, coordinate.longitude,\n" +
            "ACOS( SIN( RADIANS( latitude ) ) * SIN( RADIANS( ? ) ) + COS( RADIANS( latitude ) )\n" +
            "* COS( RADIANS( ? )) * COS( RADIANS( longitude ) - RADIANS( ? )) ) * 6380 AS distance\n" +
            "FROM coordinate\n" +
            "INNER JOIN offer on offer.coordinate_id = coordinate.id\n" +
            "WHERE\n" +
            "ACOS( SIN( RADIANS( latitude ) ) * SIN( RADIANS( ? ) ) + COS( RADIANS( latitude ) )\n" +
            "* COS( RADIANS( ? )) * COS( RADIANS( longitude ) - RADIANS( ? )) ) * 6380 < ?\n" +
            "AND offer.category_id = ?\n" +
            "AND (offer.title LIKE CONCAT('%', ?, '%')\n" +
            "OR offer.content LIKE CONCAT('%', ?, '%') )\n" +
            "ORDER BY distance\n" +
            "LIMIT " + LIMIT_NUMBER;

    private static final String BY_COORDINATE_KEYWORDS =
            "SELECT offer.id as offer_id, coordinate.id, coordinate.latitude, coordinate.longitude,\n" +
            "ACOS( SIN( RADIANS( latitude ) ) * SIN( RADIANS( ? ) ) + COS( RADIANS( latitude ) )\n" +
            "* COS( RADIANS( ? )) * COS( RADIANS( longitude ) - RADIANS( ? )) ) * 6380 AS distance\n" +
            "FROM coordinate\n" +
            "INNER JOIN offer on offer.coordinate_id = coordinate.id\n" +
            "WHERE\n" +
            "ACOS( SIN( RADIANS( latitude ) ) * SIN( RADIANS( ? ) ) + COS( RADIANS( latitude ) )\n" +
            "* COS( RADIANS( ? )) * COS( RADIANS( longitude ) - RADIANS( ? )) ) * 6380 < ?\n" +
            "AND ( offer.title LIKE CONCAT('%', ?, '%')\n" +
            "OR offer.content LIKE CONCAT('%', ?, '%') )\n" +
            "ORDER BY distance\n" +
            "LIMIT " + LIMIT_NUMBER;

    public SmartSearchService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Coordinate> findCoordinateInRadius(String userLat, String userLng, int radius) {
        return (List<Coordinate>) jdbcTemplate.query(
                BY_LOCATION,
                new Object[] {userLat, userLat, userLng, userLat, userLat, userLng, radius},
                new CoordinateMapper()
        );
    }

    public List<Coordinate> findCoordinateInRadiusWithCategoryAndKeywords(String userLat, String userLng,
                                                                           int radius, long categoryId, String keywords) {
        return (List<Coordinate>) jdbcTemplate.query(
                BY_COORDINATE_CATEGORY_KEYWORDS,
                new Object[] {userLat, userLat, userLng, userLat, userLat, userLng, radius, categoryId, keywords, keywords},
                new CoordinateMapper()
                );
    }

    public List<Coordinate> findCoordinateInRadiusWithKeywords(String userLat, String userLng,
                                                                int radius, String keywords) {
        return (List<Coordinate>) jdbcTemplate.query(
                BY_COORDINATE_KEYWORDS,
                new Object[] {userLat, userLat, userLng, userLat, userLat, userLng, radius, keywords, keywords},
                new CoordinateMapper()
        );
    }

    private static class CoordinateMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Coordinate coordinate = new Coordinate();
            coordinate.setId(rs.getLong("ID"));
            coordinate.setLatitude(rs.getString("LATITUDE"));
            coordinate.setLongitude(rs.getString("LONGITUDE"));
            coordinate.setDistance(rs.getDouble("DISTANCE"));
            coordinate.setOfferId(rs.getLong("OFFER_ID"));
            return coordinate;
        }
    }
}
