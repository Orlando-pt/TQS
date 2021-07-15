package lei.tqs.aeolus.external_api;

import lei.tqs.aeolus.external_api.weather_bit_utils.WeatherBitRequest;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

@Log4j2
public class WeatherBitSpecificMethodsIT {
    private WeatherBitAPI weatherBitAPI;

    @BeforeEach
    void setUp() {
        this.weatherBitAPI = new WeatherBitAPI(new WeatherBitRequest());
    }

    @Test
    void getHistoryAQByDayAndHourUntilPresentTest() {
        /**
         * get data from the last 2 days
         */
        var day = Calendar.getInstance();
        day.add(Calendar.DAY_OF_MONTH, -2);

        APIResponse apiResponse = this.weatherBitAPI.getHistoryAQByDayAndHourUntilPresent(
                "41.16", "-8.63", day
        );

        log.debug(apiResponse);

        // verify if the measures are all from 2 days ago until present
        for (Measure measure : apiResponse.getMeasureList()) {
            Assertions.assertThat(measure.getTimestamp().getTimeInMillis() >=
                    day.getTimeInMillis()).isTrue();
        }
    }

    @Test
    void getHistoryAQBetweenDaysTest() {
        /**
         * retrieve data from 2 days ago, ignoring todays forecasts
         */
        var start = Calendar.getInstance();
        start.add(Calendar.DAY_OF_MONTH, -2);

        var end = Calendar.getInstance();
        end.add(Calendar.DAY_OF_MONTH, -1);

        APIResponse apiResponse = this.weatherBitAPI.getHistoryAQBetweenDays(
                "41.16", "-8.63", start, end
        );

        log.debug(apiResponse);
        // verify if the measures are all from 2 days ago until present
        for (Measure measure : apiResponse.getMeasureList()) {
            Assertions.assertThat(
                    measure.getTimestamp().getTimeInMillis() >= start.getTimeInMillis() &&
                    measure.getTimestamp().getTimeInMillis() <= end.getTimeInMillis()
            ).isTrue();
        }
    }
}
