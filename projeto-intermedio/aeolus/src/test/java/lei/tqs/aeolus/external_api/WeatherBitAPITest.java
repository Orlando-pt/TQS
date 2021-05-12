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

import java.util.Calendar;
import java.util.List;

@Log4j2
@ExtendWith(MockitoExtension.class)
class WeatherBitAPITest {
    /**
     * this tests are mainly to verify if the parsing
     * between WeatherBitAPIResponse and APIResponse
     * are done correctly
     */

    // build weatherBit measures
    private final Calendar calendarLastHour = GeneralUtils.returnCalendarLastHourTimeUnix();
    private final Calendar calendarLast2Hour = GeneralUtils.returnCalendarLastHourTimeUnix();
    private final Calendar calendarLast3Hour = GeneralUtils.returnCalendarLastHourTimeUnix();
    private final Calendar calendarLast3Days = GeneralUtils.returnCalendarLastHourTimeUnix();

    private WeatherBitMeasure weatherBitMeasure1;
    private WeatherBitMeasure weatherBitMeasure2;
    private WeatherBitMeasure weatherBitMeasure3;
    private WeatherBitMeasure weatherBitMeasure4;

    private Measure measure1;
    private Measure measure2;
    private Measure measure3;
    private Measure measure4;

    @Mock
    WeatherBitRequest weatherBitRequest;

    @InjectMocks
    WeatherBitAPI weatherBitAPI;

    @BeforeEach
    void setUp() {
        this.calendarLast2Hour.set(
                Calendar.HOUR_OF_DAY, Calendar.HOUR_OF_DAY - 1
        );
        this.calendarLast3Hour.set(
                Calendar.HOUR_OF_DAY, Calendar.HOUR_OF_DAY - 2
        );
        this.calendarLast3Days.set(
                Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH - 3
        );

        this.weatherBitMeasure1 = new WeatherBitMeasure(
                253.5f, 24.9f, 108.0f, 21.0f, 45.0f, this.calendarLastHour
        );
        this.weatherBitMeasure2 = new WeatherBitMeasure(
                250.5f, 20.9f, 100.0f, 20.0f, 40.0f, this.calendarLast2Hour
        );
        this.weatherBitMeasure3 = new WeatherBitMeasure(
                251.5f, 21.9f, 101.0f, 21.0f, 41.0f, this.calendarLast3Hour
        );
        this.weatherBitMeasure4 = new WeatherBitMeasure(
                222.5f, 21.9f, 101.0f, 21.0f, 41.0f, this.calendarLast3Days
        );

        this.measure1 = new Measure(253.5f, 24.9f, 108.0f, 21.0f, 45.0f, this.calendarLastHour);
        this.measure2 = new Measure(250.5f, 20.9f, 100.0f, 20.0f, 40.0f, this.calendarLast2Hour);
        this.measure3 = new Measure(251.5f, 21.9f, 101.0f, 21.0f, 41.0f, this.calendarLast3Hour);
        this.measure4 = new Measure(222.5f, 21.9f, 101.0f, 21.0f, 41.0f, this.calendarLast3Days);
    }

    @Test
    void getCurrentAQTest() {
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
        Assertions.assertThat(
                apiResponse.getMeasureList()
        ).contains(this.measure1);
    }

    @Test
    void getHistoryAQPreviousDaysTest() {
        WeatherBitAPIResponse response = this.getObjectsOriginatedByTheApiRequestHistory();

        Mockito.when(weatherBitRequest.getHistoryDataFromAPI("41.16", "-8.63")).thenReturn(
                response
        );

        APIResponse apiResponse = this.weatherBitAPI.getHistoryAQPreviousDays("41.16", "-8.63", 1);

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
        Assertions.assertThat(
                apiResponse.getMeasureList()
        ).contains(this.measure1, this.measure2, this.measure3);

        // doesn't contain the measure from 3 days ago
        Assertions.assertThat(
                apiResponse.getMeasureList()
        ).doesNotContain(this.measure4);

        Assertions.assertThat(apiResponse.getMeasureList()).hasSize(3);
    }

    // TODO fazer verificacão de pedido superior a 3 dias
    @Test
    void emptyResponse_whenMakeRequestGreaterThan3Days() {
        APIResponse apiResponse = this.weatherBitAPI.getHistoryAQPreviousDays(
                "1", "2", 4
        );

        log.debug(apiResponse);

        Assertions.assertThat(
                apiResponse.empty()
        ).isTrue();

        // verifying with the Chrono class
        var notToday = Calendar.getInstance();
        notToday.add(Calendar.DAY_OF_MONTH, -4);

        apiResponse = this.weatherBitAPI.getHistoryAQByDayAndHourUntilPresent(
                "1", "2", notToday
        );

        Assertions.assertThat(
                apiResponse.empty()
        ).isTrue();
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
        var data = List.of(this.weatherBitMeasure1);
        weatherBitAPIResponse.setData(data);

        return weatherBitAPIResponse;
    }

    private WeatherBitAPIResponse getObjectsOriginatedByTheApiRequestHistory() {
        // Object (WeatherBitApiResponse) returned when calling the api
        WeatherBitAPIResponse weatherBitAPIResponse = new WeatherBitAPIResponse();

        // set coordinates
        weatherBitAPIResponse.setLon("-8.63");
        weatherBitAPIResponse.setLat("41.16");

        // other irrelevant params
        weatherBitAPIResponse.setCity_name("Porto");
        weatherBitAPIResponse.setCountry_code("PT");
        weatherBitAPIResponse.setState_code("17");

        // set measures
        var data = List.of(this.weatherBitMeasure1,
                this.weatherBitMeasure2,
                this.weatherBitMeasure3,
                this.weatherBitMeasure4);
        weatherBitAPIResponse.setData(data);

        return weatherBitAPIResponse;
    }
}