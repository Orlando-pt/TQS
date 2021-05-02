package lei.tqs.aeolus.external_api;

import lei.tqs.aeolus.external_api.open_weather_utils.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class OpenWeatherAPITest {

    @Mock
    OpenWeatherRequest openWeatherRequest;

    @InjectMocks
    OpenWeatherAPI openWeatherAPI;


    @BeforeEach
    void setUp() {
    }

    @Test
    void getCurrentAQ() {
        // TODO continuar neste teste
        OpenWeatherResponse responseAPI = getObjectsOriginatedByTheAPIRequestCurrent();

        Mockito.when(
                this.openWeatherRequest.getCurrentAQFromAPI("40.8661", "-8.6457")
        ).thenReturn(responseAPI);

        APIResponse apiResponse = this.openWeatherAPI.getCurrentAQ("40.8661", "-8.6457");

        System.out.println(apiResponse);

        Assertions.assertThat(apiResponse.getLatitude())
                .isEqualTo("40.8661");
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

    private OpenWeatherResponse getObjectsOriginatedByTheAPIRequestCurrent() {
        // Object (OpenWeatherResponse) returned when calling the api
        OpenWeatherResponse openWeatherResponse = new OpenWeatherResponse();

        // set coordinates
        openWeatherResponse.setCoord(new Coordinate("-8.6457", "40.8661"));

        // set measures
        var list = List.of(new OpenWeatherData(
                new OpenWeatherMeasure(188.59f, 0.92f, 121.59f, 1.48f, 4.1f),
                1619982000
        ));
        openWeatherResponse.setList(list);

        return openWeatherResponse;
    }
}