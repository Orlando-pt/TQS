package lei.tqs.aeolus.external_api;

import lei.tqs.aeolus.external_api.weather_bit_utils.WeatherBitAPIResponse;
import lei.tqs.aeolus.external_api.weather_bit_utils.WeatherBitMeasure;
import lei.tqs.aeolus.external_api.weather_bit_utils.WeatherBitRequest;
import lei.tqs.aeolus.utils.GeneralUtils;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
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
class WeatherBitAPITest {

    @Mock
    WeatherBitRequest weatherBitRequest;

    @InjectMocks
    WeatherBitAPI weatherBitAPI;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {}

    @Test
    void getCurrentAQ() {
        WeatherBitAPIResponse response = this.getObjectsOriginatedByTheAPIRequestCurrent();

        Mockito.when(weatherBitRequest.getCurrentDataFromAPI("41.16", "-8.63")).thenReturn(
                response
        );

        APIResponse apiResponse = this.weatherBitAPI.getCurrentAQ("41.16", "-8.63");

        log.info(apiResponse);

        /**
         * verify if all the fields are correct
         */
        Assertions.assertThat(
                apiResponse.getLatitude()
        ).isEqualTo("41.16");

        Assertions.assertThat(
                apiResponse.getLongitude()
        ).isEqualTo("-8.63");

            // expected measure
        var expectedMeasure = new Measure(253.5f, 24.9f, 108.0f, 21.0f, 45.0f, GeneralUtils.returnCalendarLastHourTimeUnix());
        Assertions.assertThat(
                apiResponse.getMeasureList()
        ).contains(expectedMeasure);
    }

    @Test
    void getHistoryAQPreviousDays() {
    }

    @Test
    void getHistoryAQByDayAndHourUntilPresent() {
    }

    @Test
    void getHistoryAQBetweenDays() {
    }

    @Test
    void getHistoryAQBetweenHours() {
    }

    @Test
    void getHistoryAQBetweenDaysWithHours() {
    }

    /**
     * this is a representation of the api response
     */
    private WeatherBitAPIResponse getObjectsOriginatedByTheAPIRequestCurrent() {
        // Object (OpenWeatherResponse) returned when calling the api
        WeatherBitAPIResponse weatherBitAPIResponse = new WeatherBitAPIResponse();

        // set coordinates
        weatherBitAPIResponse.setLon("-8.63");
        weatherBitAPIResponse.setLat("41.16");

        // other irrelevant params
        weatherBitAPIResponse.setCity_name("Porto");
        weatherBitAPIResponse.setCountry_code("PT");
        weatherBitAPIResponse.setState_code("17");

        // set measures
        var data = List.of(new WeatherBitMeasure(
                253.5f, 24.9f, 108.0f, 21.0f, 45.0f, GeneralUtils.returnCalendarLastHourTimeUnix()
        ));
        weatherBitAPIResponse.setData(data);

        return weatherBitAPIResponse;
    }
}