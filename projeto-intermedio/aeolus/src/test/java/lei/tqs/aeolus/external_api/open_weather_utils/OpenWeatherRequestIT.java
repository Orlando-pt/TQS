package lei.tqs.aeolus.external_api.open_weather_utils;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OpenWeatherRequestIT {
    private OpenWeatherRequest openWeatherRequest;

    @BeforeEach
    void setUp() {
        this.openWeatherRequest = new OpenWeatherRequest();
    }

    @AfterEach
    void tearDown() {
        this.openWeatherRequest = null;
    }

    @Test
    void getCurrentAQFromAPITest() {
        OpenWeatherResponse response = this.openWeatherRequest.getCurrentAQFromAPI("40.866057889889206", "-8.645710577339893");
        System.out.println(response);

        MatcherAssert.assertThat(response.getCoord().getLat(), CoreMatchers.is("40.8661"));
    }

    @Test
    void getHistoryAQFromAPITest() {
        OpenWeatherResponse response = this.openWeatherRequest.getHistoryAQFromAPI("40.866057889889206", "-8.645710577339893");
        System.out.println(response);

        MatcherAssert.assertThat(response.getCoord().getLat(), CoreMatchers.is("40.8661"));
    }

}