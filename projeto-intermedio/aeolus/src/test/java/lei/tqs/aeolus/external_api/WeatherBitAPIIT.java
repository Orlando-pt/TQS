package lei.tqs.aeolus.external_api;

import lei.tqs.aeolus.external_api.weather_bit_utils.WeatherBitAPIResponse;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WeatherBitAPIIT {

    private WeatherBitAPI weatherBitAPI;

    @BeforeEach
    void setUp() {
        this.weatherBitAPI = new WeatherBitAPI();
    }

    @AfterEach
    void tearDown() {
        this.weatherBitAPI = null;
    }

    @Test
    void getCurrentDataFromAPITest() {
        /**
         * testing if the api is responsive and transmitting current air quality data
         * Integration test
         */
        WeatherBitAPIResponse response = this.weatherBitAPI.getCurrentDataFromAPI("41.16225097228622", "-8.628822176072289");
        System.out.println(response);

        MatcherAssert.assertThat(response.getCity_name(), CoreMatchers.is("Porto"));
    }

    @Test
    void getHistoryDataFromAPI() {
        /**
         * testing if the api is responsive and transmitting historical air quality data
         * Integration test
         */
        WeatherBitAPIResponse response = this.weatherBitAPI.getHistoryDataFromAPI("40.866057889889206", "-8.645710577339893");
        System.out.println(response);

        MatcherAssert.assertThat(response.getCity_name(), CoreMatchers.is("Ovar"));
    }
}
