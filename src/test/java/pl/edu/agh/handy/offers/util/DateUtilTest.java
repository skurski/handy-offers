package pl.edu.agh.handy.offers.util;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * Created by psk on 14.06.17.
 */
public class DateUtilTest {

    @Test
    public void convertToLocalDateTimeTest() {
        String dateTime = "2017-06-12 22:00";
        LocalDateTime convertedDateTime = DateUtil.convertToLocalDateTime(dateTime);

        Assert.assertEquals(2017, convertedDateTime.getYear());
        Assert.assertEquals(6, convertedDateTime.getMonthValue());
        Assert.assertEquals(12, convertedDateTime.getDayOfMonth());
        Assert.assertEquals(22, convertedDateTime.getHour());
        Assert.assertEquals(0, convertedDateTime.getMinute());
    }
}
