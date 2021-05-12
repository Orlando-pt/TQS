package lei.tqs.aeolus.external_api;

import lei.tqs.aeolus.external_api.open_weather_utils.*;
import lei.tqs.aeolus.utils.GeneralUtils;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@Log4j2
@ExtendWith(MockitoExtension.class)
class OpenWeatherAPITest {

    @Mock
    OpenWeatherRequest openWeatherRequest;

    @InjectMocks
    OpenWeatherAPI openWeatherAPI;


    @BeforeEach
    void setUp() {
    }

    @Test
    void getCurrentAQTest() {
        OpenWeatherResponse responseAPI = getObjectsOriginatedByTheAPIRequestCurrent();

        Mockito.when(
                this.openWeatherRequest.getCurrentAQFromAPI("40.8661", "-8.6457")
        ).thenReturn(responseAPI);

        APIResponse apiResponse = this.openWeatherAPI.getCurrentAQ("40.8661", "-8.6457");

        log.info(apiResponse);

        /**
         * verify if all the fields are correct
         */
        Assertions.assertThat(apiResponse.getLatitude())
                .isEqualTo("40.8661");

        Assertions.assertThat(
                apiResponse.getLongitude()
        ).isEqualTo("-8.6457");

        // expected measure
        var expectedMeasure = new Measure(188.59f, 0.92f, 121.59f, 1.48f, 4.1f, GeneralUtils.returnCalendarOfTimeUnix(1619982000L));
        Assertions.assertThat(
                apiResponse.getMeasureList()
        ).contains(expectedMeasure);
    }

    @Test
    void getHistoryAQPreviousDaysTest() {
        OpenWeatherResponse openWeatherResponse = this.getObjectsOriginatedByTheAPIRequestHistory();

        // TODO verificar o parâmetro dos 3 dias

        Mockito.when(
                openWeatherRequest.getHistoryAQFromAPI("1", "2", 3)
        ).thenReturn(openWeatherResponse);

        var apiResponse = this.openWeatherAPI.getHistoryAQPreviousDays("1", "2", 3);

        log.info(apiResponse);

        /**
         * verify if all the fields are correct
         */
        Assertions.assertThat(apiResponse.getLatitude())
                .isEqualTo("1");

        Assertions.assertThat(
                apiResponse.getLongitude()
        ).isEqualTo("2");

        // expected measure
        var expectedMeasure = new Measure(188.59f, 0.92f, 121.59f, 1.48f, 4.1f, GeneralUtils.returnCalendarOfTimeUnix(1619982000L));
        var secondExpectedMeasure = new Measure(180.59f, 0.90f, 120.50f, 1.08f, 3.5f, GeneralUtils.returnCalendarOfTimeUnix(1619962000L));

        Assertions.assertThat(
                apiResponse.getMeasureList()
        ).contains(expectedMeasure, secondExpectedMeasure);
    }

    @Test
    void getHistoryAQByDayAndHourUntilPresentTest() {
        /**
         * get historic data from a precise day and hour until the present
         */

    }

    @Test
    void getHistoryAQBetweenDaysTest() {
        /**
         * get historic data between two days
         */
    }

    @Test
    void getHistoryAQBetweenHoursTest() {
        /**
         * get historic data between hours on a determined day
         */
    }

    @Test
    void getHistoryAQBetweenDaysWithHoursTest() {
        /**
         * get historic data between days with the initial hour and final hour
         */
    }

    /**
     * this is a representation of the api response
     */
    private OpenWeatherResponse getObjectsOriginatedByTheAPIRequestCurrent() {
        // Object (OpenWeatherResponse) returned when calling the api
        OpenWeatherResponse openWeatherResponse = new OpenWeatherResponse();

        // set coordinates
        openWeatherResponse.setCoord(new Coordinate("-8.6457", "40.8661"));

        // set measures
        var list = List.of(new OpenWeatherData(
                new OpenWeatherMeasure(188.59f, 0.92f, 121.59f, 1.48f, 4.1f),
                1619982000
        ));
        openWeatherResponse.setList(list);

        return openWeatherResponse;
    }

    private OpenWeatherResponse getObjectsOriginatedByTheAPIRequestHistory() {
        var openWeatherResponse = new OpenWeatherResponse();

        // set coordinates
        openWeatherResponse.setCoord(new Coordinate("2", "1"));

        // set measures
        var list = List.of(new OpenWeatherData(
                new OpenWeatherMeasure(188.59f, 0.92f, 121.59f, 1.48f, 4.1f),
                1619982000
        ),
                new OpenWeatherData(
                        new OpenWeatherMeasure(180.59f, 0.90f, 120.50f, 1.08f, 3.5f),
                        1619962000
                ));
        openWeatherResponse.setList(list);

        return openWeatherResponse;
    }

}