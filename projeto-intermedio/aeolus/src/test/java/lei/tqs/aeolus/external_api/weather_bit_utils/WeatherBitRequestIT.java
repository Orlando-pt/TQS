package lei.tqs.aeolus.external_api.weather_bit_utils;

import lombok.extern.log4j.Log4j2;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
class WeatherBitRequestIT {
    private WeatherBitRequest weatherBitRequest;

    @BeforeEach
    void setUp() {
        this.weatherBitRequest = new WeatherBitRequest();
    }

    @Test
    void getCurrentDataFromAPITest() {
        /**
         * testing if the api is responsive and transmitting current air quality data
         * Integration test
         */
        WeatherBitAPIResponse response = this.weatherBitRequest.getCurrentDataFromAPI("41.16225097228622", "-8.628822176072289");
        log.info(response);

        MatcherAssert.assertThat(response.getCity_name(), CoreMatchers.is("Porto"));
    }

    @Test
    void getHistoryDataFromAPI() {
        /**
         * testing if the api is responsive and transmitting historical air quality data
         * Integration test
         */
        WeatherBitAPIResponse response = this.weatherBitRequest.getHistoryDataFromAPI("40.866057889889206", "-8.645710577339893");
        log.info(response);

        MatcherAssert.assertThat(response.getCity_name(), CoreMatchers.is("Ovar"));
    }
}