package lei.tqs.aeolus.external_api;

import lei.tqs.aeolus.external_api.open_weather_utils.OpenWeatherResponse;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OpenWeatherAPIIT {

    private OpenWeatherAPI openWeatherAPI;

    @BeforeEach
    void setUp() {
        this.openWeatherAPI = new OpenWeatherAPI();
    }

    @AfterEach
    void tearDown() {
        this.openWeatherAPI = null;
    }

    @Test
    void getCurrentAQFromAPITest() {
        OpenWeatherResponse response = this.openWeatherAPI.getCurrentAQFromAPI("40.866057889889206", "-8.645710577339893");
        System.out.println(response);

        MatcherAssert.assertThat(response.getCoord().getLat(), CoreMatchers.is("40.8661"));
    }

    @Test
    void getHistoryAQFromAPITest() {
        OpenWeatherResponse response = this.openWeatherAPI.getHistoryAQFromAPI("40.866057889889206", "-8.645710577339893");
        System.out.println(response);

        MatcherAssert.assertThat(response.getCoord().getLat(), CoreMatchers.is("40.8661"));
    }

}
