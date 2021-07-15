package lei.tqs.aeolus.external_api;

import lei.tqs.aeolus.external_api.open_weather_utils.OpenWeatherRequest;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

@Log4j2
public class OpenWeatherSpecificMethodsIT {

    private OpenWeatherAPI openWeatherAPI;

    @BeforeEach
    void setUp() {
        this.openWeatherAPI = new OpenWeatherAPI(new OpenWeatherRequest());
    }

    @Test
    void getHistoryAQByDayAndHourUntilPresentTest() {
        /**
         * get historic data from a precise day and hour until the present
         * get the forecast since yesterday at 5PM
         */
        var calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 17);

        var startTime = calendar.getTimeInMillis() / 1000L;

        var apiResponse = this.openWeatherAPI.getHistoryAQByDayAndHourUntilPresent("41.16", "-8.63", calendar);

        log.debug(apiResponse);

        var measures = apiResponse.getMeasureList();

        for (Measure m : measures) {
            Assertions.assertThat(
                    m.getTimestamp().getTimeInMillis() >= startTime
            ).isTrue();
        }
    }

    @Test
    void getHistoryAQBetweenDaysTest() {
        /**
         * get historic data between two days
         * get data 2 days before and one day before
         */
        var initial = Calendar.getInstance();
        initial.add(Calendar.DAY_OF_MONTH,-2);

        var end = Calendar.getInstance();
        end.add(Calendar.DAY_OF_MONTH,-1);

        var apiResponse = this.openWeatherAPI.getHistoryAQBetweenDays("41.16", "-8.63", initial, end);

        var initialUnixTime = initial.getTimeInMillis();
        var endUnixTime = end.getTimeInMillis();

        for (Measure measure : apiResponse.getMeasureList()) {
            Assertions.assertThat(
                    measure.getTimestamp().getTimeInMillis() >= initialUnixTime &&
                            measure.getTimestamp().getTimeInMillis() <= endUnixTime
            ).isTrue();
        }
    }

}
