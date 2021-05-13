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
    Cache cache;

    @Mock(lenient = true)
    OpenWeatherAPI openWeatherAPI;

    @Mock(lenient = true)
    WeatherBitAPI weatherBitAPI;

    @InjectMocks()
    private AqService aqService;

    private APIResponse response;
    private ImmutablePair<String, String> location;

    @BeforeEach
    void setUp() {
        this.response = new APIResponse();
        this.response.setLatitude("1");
        this.response.setLongitude("2");
        this.response.addMeasure(new Measure(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, Calendar.getInstance()));

        this.location = ImmutablePair.of(this.response.getLatitude(), this.response.getLongitude());
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.cache, this.weatherBitAPI, this.openWeatherAPI);
    }

    @Test
    void getCurrentAQ_fromCache() {
        Mockito.when(this.cache.get(this.location)).thenReturn(Optional.of(this.response));

        APIResponse apiResponse = this.aqService.getCurrentAQ("1", "2");

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

        APIResponse apiResponse = this.aqService.getCurrentAQ("1", "2");

        Assertions.assertThat(apiResponse).isEqualTo(
                this.response
        );

        Mockito.verify(this.cache, Mockito.times(1)).get(this.location);
        Mockito.verify(this.openWeatherAPI, Mockito.times(1)).getCurrentAQ("1", "2");
    }
}