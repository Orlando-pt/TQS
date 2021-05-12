package lei.tqs.aeolus.utils;

import java.util.Calendar;

public class GeneralUtils {
    public static Calendar returnCalendarOfTimeUnix(long time) {
        var calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000L);
        return calendar;
    }

    public static Calendar returnCalendarLastHourTimeUnix() {
        var calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        return calendar;
    }

    public static long calculateCurrentDt() {
        var calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis() / 1000L;
    }
}
