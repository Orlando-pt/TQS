package lei.tqs.aeolus.utils;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TimeConverterTest {

    @Test
    void dateFromTimeUnixTest() {
        /**
         * test if the utility that converts date from unix time
         * is working properly
         */
        Calendar day = TimeConverter.dateFromTimeUnix(1619456400);
        System.out.println(day);
        MatcherAssert.assertThat(day.get(Calendar.DAY_OF_MONTH), CoreMatchers.is(26));
    }
}