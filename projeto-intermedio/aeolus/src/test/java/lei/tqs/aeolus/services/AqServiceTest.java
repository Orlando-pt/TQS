package lei.tqs.aeolus.services;

import lei.tqs.aeolus.cache.Cache;
import lei.tqs.aeolus.external_api.APIResponse;
import lei.tqs.aeolus.external_api.Measure;
import lei.tqs.aeolus.external_api.OpenWeatherAPI;
import lei.tqs.aeolus.external_api.WeatherBitAPI;
import org.apache.commons.lang3.tuple.ImmutablePair;
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
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AqServiceTest {

    @Mock(lenient = true)
    Cache<ImmutablePair<String, String>, APIResponse> cache;

    @Mock(lenient = true)
    OpenWeatherAPI openWeatherAPI;

    @Mock(lenient = true)
    WeatherBitAPI weatherBitAPI;

    @InjectMocks()
    private AqService aqService;

    private APIResponse response;
    private APIResponse responseWithVariousMeasures;
    private ImmutablePair<String, String> location;
    private Calendar today;
    private Calendar yesterday;

    @BeforeEach
    void setUp() {
        this.response = new APIResponse();
        this.responseWithVariousMeasures = new APIResponse();
        this.response.setLatitude("1");
        this.response.setLongitude("2");
        this.response.addMeasure(new Measure(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, Calendar.getInstance()));

        this.responseWithVariousMeasures.setLatitude("1");
        this.responseWithVariousMeasures.setLongitude("2");
        this.responseWithVariousMeasures.addMeasure(new Measure(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, Calendar.getInstance()));
        this.responseWithVariousMeasures.addMeasure(new Measure(2.0f, 2.0f, 2.0f, 2.0f, 2.0f, Calendar.getInstance()));

        this.location = ImmutablePair.of(this.response.getLatitude(), this.response.getLongitude());
        this.today = Calendar.getInstance();
        this.yesterday = Calendar.getInstance();
        this.yesterday.add(Calendar.DAY_OF_MONTH, -1);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.cache, this.weatherBitAPI, this.openWeatherAPI);
    }

    @Test
    void getCurrentAQ_fromCache() {
        Mockito.when(this.cache.get(this.location)).thenReturn(Optional.of(this.response));

        var apiResponse = this.aqService.getCurrentAQ("1", "2");

        Assertions.assertThat(apiResponse).isEqualTo(
                this.response
        );

        Mockito.verify(this.cache, Mockito.times(1)).get(this.location);
    }

    @Test
    void getCurrentAQ_fromOpenWeatherApi() {
        // cache won't have location data
        Mockito.when(this.cache.get(this.location)).thenReturn(
                Optional.empty()
        );

        // openweather will respond
        Mockito.when(this.openWeatherAPI.getCurrentAQ("1", "2")).thenReturn(
                this.response
        );

        var apiResponse = this.aqService.getCurrentAQ("1", "2");

        Assertions.assertThat(apiResponse).isEqualTo(
                this.response
        );

        Mockito.verify(this.cache, Mockito.times(1)).get(this.location);
        Mockito.verify(this.openWeatherAPI, Mockito.times(1)).getCurrentAQ("1", "2");
    }

    @Test
    void getCurrentAQ_fromWeatherBitApi() {
        // cache won't have location data
        Mockito.when(this.cache.get(this.location)).thenReturn(
                Optional.empty()
        );

        // openweather will be unavailable
        Mockito.when(this.openWeatherAPI.getCurrentAQ("1", "2")).thenReturn(
                new APIResponse()
        );

        // weather bit will respond
        Mockito.when(this.weatherBitAPI.getCurrentAQ("1", "2")).thenReturn(
                this.response
        );

        var apiResponse = this.aqService.getCurrentAQ("1", "2");

        Assertions.assertThat(apiResponse).isEqualTo(
                this.response
        );

        Mockito.verify(this.cache, Mockito.times(1)).get(this.location);
        Mockito.verify(this.openWeatherAPI, Mockito.times(1)).getCurrentAQ("1", "2");
        Mockito.verify(this.openWeatherAPI, Mockito.times(1)).getCurrentAQ("1", "2");
    }

    @Test
    void correctCoordinatesTest() {
        // Invalid latitude should return empty response
        var apiResponse = this.aqService.getCurrentAQ("100", "2");

        Assertions.assertThat(apiResponse.empty()).isTrue();

        // Invalid longitude should return empty response
        apiResponse = this.aqService.getCurrentAQ("1", "200");

        Assertions.assertThat(apiResponse.empty()).isTrue();

        // Test a String not containing double element, it will generate an exception
        apiResponse = this.aqService.getCurrentAQ("1as", "2");

        Assertions.assertThat(apiResponse.empty()).isTrue();
    }

    @Test
    void getHistoryAQPreviousDays_defaultOpenWeatherAnswers() {
        Mockito.when(this.openWeatherAPI.getHistoryAQPreviousDays("1", "2", 2)).thenReturn(
                this.responseWithVariousMeasures
        );

        Mockito.when(this.weatherBitAPI.getHistoryAQPreviousDays(
                "1", "2", 2
        )).thenReturn(this.responseWithVariousMeasures);

        var apiResponse = this.aqService.getHistoryAQPreviousDays(
                "1", "2", 2
        );

        Assertions.assertThat(
                apiResponse
        ).isEqualTo(this.responseWithVariousMeasures);

        Mockito.verify(this.openWeatherAPI, Mockito.times(1)).getHistoryAQPreviousDays(
                "1", "2", 2
        );

        Mockito.verify(this.weatherBitAPI, Mockito.times(0)).getHistoryAQPreviousDays(
                "1", "2", 2
        );
    }

    @Test
    void getHistoryAQByDayAndHourUntilPresent_fromWeatherBit() {
        Mockito.when(this.openWeatherAPI.getHistoryAQByDayAndHourUntilPresent("1", "2", this.today)).thenReturn(
                new APIResponse()
        );

        Mockito.when(this.weatherBitAPI.getHistoryAQByDayAndHourUntilPresent(
                "1", "2", this.today
        )).thenReturn(this.responseWithVariousMeasures);

        var apiResponse = this.aqService.getHistoryAQByDayAndHourUntilPresent(
                "1", "2", today
        );

        Assertions.assertThat(
                apiResponse
        ).isEqualTo(this.responseWithVariousMeasures);

        Mockito.verify(this.openWeatherAPI, Mockito.times(1)).getHistoryAQByDayAndHourUntilPresent(
                "1", "2", this.today
        );

        Mockito.verify(this.weatherBitAPI, Mockito.times(1)).getHistoryAQByDayAndHourUntilPresent(
                "1", "2", this.today
        );
    }

    @Test
    void getHistoryAQBetweenDays_fromWeatherBitApi() {
        Mockito.when(this.openWeatherAPI.getHistoryAQBetweenDays(
                "1", "2", this.yesterday, this.today)).thenReturn(
                new APIResponse()
        );

        Mockito.when(this.weatherBitAPI.getHistoryAQBetweenDays(
                "1", "2", this.yesterday, this.today
        )).thenReturn(this.responseWithVariousMeasures);

        var apiResponse = this.aqService.getHistoryAQBetweenDays(
                "1", "2", this.yesterday, this.today
        );

        Assertions.assertThat(
                apiResponse
        ).isEqualTo(this.responseWithVariousMeasures);

        Mockito.verify(this.openWeatherAPI, Mockito.times(1)).getHistoryAQBetweenDays(
                "1", "2", this.yesterday, this.today
        );

        Mockito.verify(this.weatherBitAPI, Mockito.times(1)).getHistoryAQBetweenDays(
                "1", "2", this.yesterday, this.today
        );
    }

    @Test
    void cacheSizeTest() {
        Mockito.when(this.cache.size()).thenReturn(20);

        Assertions.assertThat(
                this.aqService.cacheSize()
        ).isEqualTo(20);

        Mockito.verify(this.cache, Mockito.times(1)).size();
    }

    /**
     * the rest of the methods were already tested
     * on the specific test classes
     */
}