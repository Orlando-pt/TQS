package lei.tqs.aeolus.external_api.open_weather_utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OpenWeatherRequestTest {
    /**
     * check the occurence of erros while making request to api
     */

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    OpenWeatherRequest openWeatherRequest;

    @Test
    void exceptionOccuredWhileContactingApi_thenReturnEmptyResponse() {
        var url = "http://api.openweathermap.org/data/2.5/air_pollution?lat=1&lon=2&appid=00ae9df8c755778aea621c4543cf6b25";

        Mockito.when(restTemplate.getForObject(url, OpenWeatherResponse.class)).thenThrow(
                new RestClientException("Api went to china and doesn't respond.")
        );

        Assertions.assertThat(
                openWeatherRequest.getCurrentAQFromAPI("1", "2").empty()
        ).isTrue();

        Mockito.verify(restTemplate, Mockito.times(1)).getForObject(
                url, OpenWeatherResponse.class
        );
    }
}