package lei.tqs.aeolus.utils;

import java.util.Calendar;

public class TimeConverter {

    private TimeConverter() {
        throw new IllegalStateException("Utility class");
    }

    public static Calendar dateFromTimeUnix(long timeUnix) {
        var calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeUnix * 1000);
        return calendar;
    }
}
