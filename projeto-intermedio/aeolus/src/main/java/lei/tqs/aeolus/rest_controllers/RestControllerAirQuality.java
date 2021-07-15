package lei.tqs.aeolus.rest_controllers;

import lei.tqs.aeolus.external_api.APIResponse;
import lei.tqs.aeolus.services.AqServiceInterface;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Calendar;

@Log4j2
@RestController
@RequestMapping("/api/aq")
public class RestControllerAirQuality {

    @Autowired
    private AqServiceInterface aqService;

    @GetMapping("/currentaq")
    @ResponseBody
    public APIResponse getCurrentAirQuality(@RequestParam String lat, @RequestParam String lng) {
        log.info(String.format("Request : /api/aq/currentaq?lat=%s&lng=%s", lat, lng));
        return this.aqService.getCurrentAQ(lat, lng);
    }

    @GetMapping("/history")
    @ResponseBody
    public APIResponse getHistoryAirQuality(
            @RequestParam String lat,
            @RequestParam String lng,
            @RequestParam Integer days) {
        log.info(String.format("Request : /api/aq/history?lat=%s&lng=%s&days=%d", lat, lng, days));
        return this.aqService.getHistoryAQPreviousDays(
                lat,
                lng,
                days
        );
    }

    @GetMapping("/fromday")
    @ResponseBody
    public APIResponse getHistoryAirQualityFromDay(
            @RequestParam String lat,
            @RequestParam String lng,
            @RequestParam Integer year,
            @RequestParam Integer month,
            @RequestParam Integer day,
            @RequestParam Integer hour
    ) {
        log.info(String.format("Request : /api/aq/fromday?lat=%s&lng=%s&year=%s" +
                "&month=%s&day=%s&hour=%s", lat, lng, year, month, day, hour
        ));
        var calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year,
                month-1,
                day,
                hour,
                0);
        return this.aqService.getHistoryAQByDayAndHourUntilPresent(
                lat, lng, calendar);
    }

    @GetMapping("/betweendays")
    @ResponseBody
    public APIResponse getHistoryAirQualityBetweenDays(
            @RequestParam String lat,
            @RequestParam String lng,
            @RequestParam Integer initialYear,
            @RequestParam Integer initialMonth,
            @RequestParam Integer initialDay,
            @RequestParam Integer initialHour,
            @RequestParam Integer endYear,
            @RequestParam Integer endMonth,
            @RequestParam Integer endDay,
            @RequestParam Integer endHour
    ) {
        log.info(String.format("Request : /api/aq/fromday?lat=%s&lng=%s&initialYear=%s" +
                "&initialMonth=%s&initialDay=%s&initialHour=%s&endYear=%s" +
                "&endMonth=%s&endDay=%s&endHour=%s", lat, lng, initialYear, initialMonth,
                initialDay, initialHour, endYear, endMonth, endDay, endHour
        ));

        var initial = Calendar.getInstance();
        var end = Calendar.getInstance();
        initial.clear();
        end.clear();
        initial.set(initialYear, initialMonth-1, initialDay, initialHour, 0);
        end.set(endYear, endMonth-1, endDay, endHour, 0);


        return this.aqService.getHistoryAQBetweenDays(
                lat, lng, initial, end
        );
    }

}
