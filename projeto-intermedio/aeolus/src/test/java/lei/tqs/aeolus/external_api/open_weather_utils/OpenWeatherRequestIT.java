package lei.tqs.aeolus.external_api.open_weather_utils;

import lombok.extern.log4j.Log4j2;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Log4j2
class OpenWeatherRequestIT {
    private OpenWeatherRequest openWeatherRequest;

    @BeforeEach
    void setUp() {
        this.openWeatherRequest = new OpenWeatherRequest();
    }

    @Test
    void getCurrentAQFromAPITest() {
        OpenWeatherResponse response = this.openWeatherRequest.getCurrentAQFromAPI("40.866057889889206", "-8.645710577339893");
        log.info(response);

        MatcherAssert.assertThat(response.getCoord().getLat(), CoreMatchers.is("40.8661"));
    }

    @Test
    void getHistoryAQFromAPITest() {
        OpenWeatherResponse response = this.openWeatherRequest.getHistoryAQFromAPI("40.866057889889206", "-8.645710577339893", 3);
        log.info(response);

        MatcherAssert.assertThat(response.getCoord().getLat(), CoreMatchers.is("40.8661"));
    }

}