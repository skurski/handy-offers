package pl.edu.agh.handy.offers.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for handle dates operation.
 */
public class DateUtil {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    public static LocalDateTime convertToLocalDateTime(String date) {
        return LocalDateTime.parse(date, dateFormatter);
    }
}
