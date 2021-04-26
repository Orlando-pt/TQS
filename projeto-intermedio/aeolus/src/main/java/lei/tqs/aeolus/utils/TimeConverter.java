package lei.tqs.aeolus.utils;

import java.util.Calendar;

public class TimeConverter {

    public static Calendar dateFromTimeUnix(long timeUnix) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeUnix * 1000);
        return calendar;
    }
}
