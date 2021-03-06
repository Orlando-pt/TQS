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
        var calendar = returnCalendarLastHourTimeUnix();
        return calendar.getTimeInMillis() / 1000L;
    }

    public static boolean verifyLatitude(String field) {
        try {
            if (Math.abs(Double.parseDouble(field)) <= 90)
                return true;
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean verifyLongitude(String field) {
        try {
            if (Math.abs(Double.parseDouble(field)) <= 180)
                return true;
        } catch (Exception e) {
        }
        return false;
    }

    public static String cropCoordinate(String field) {
        if (!field.contains("."))
            return field;

        var values = field.split("\\.");

        if (values[1].length() <= 4)
            return field;

        return values[0] + "." + values[1].substring(0, 4);
    }
}
