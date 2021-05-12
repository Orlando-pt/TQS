package lei.tqs.aeolus.external_api;

import java.util.Calendar;

public interface ExternalApiInterface {

    APIResponse getCurrentAQ(String lat, String lng);

    APIResponse getHistoryAQPreviousDays(String lat, String lng, int days);

    APIResponse getHistoryAQByDayAndHourUntilPresent(String lat, String lng, Calendar day, int hour);

    APIResponse getHistoryAQBetweenDays(String lat, String lng, Calendar initial, Calendar end);

    APIResponse getHistoryAQBetweenHours(String lat, String lng, Calendar day, int initial, int end);

    APIResponse getHistoryAQBetweenDaysWithHours(String lat, String lng, Calendar initial, Calendar end, int initialHour, int finalHour);
}
