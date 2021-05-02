package lei.tqs.aeolus.external_api;

import java.util.Date;

public interface ExternalApiInterface {

    APIResponse getCurrentAQ(String lat, String lng);

    APIResponse getHistoryAQPreviousDays(String lat, String lng, int days);

    APIResponse getHistoryAQByDayAndHourUntilPresent(String lat, String lng, Date day, int hour);

    APIResponse getHistoryAQBetweenDays(String lat, String lng, Date initial, Date end);

    APIResponse getHistoryAQBetweenHours(String lat, String lng, Date day, int initial, int end);

    APIResponse getHistoryAQBetweenDaysWithHours(String lat, String lng, Date initial, Date end, int initialHour, int finalHour);
}
