package lei.tqs.aeolus.ExternalAPI;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeatherBitAPITest {

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
    void getCurrentAQ() {
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
}