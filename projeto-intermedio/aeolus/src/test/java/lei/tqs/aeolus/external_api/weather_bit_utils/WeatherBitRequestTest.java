package lei.tqs.aeolus.external_api.weather_bit_utils;

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
class WeatherBitRequestTest {
    /**
     * check the occurence of erros while making request to api
     */

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    WeatherBitRequest weatherBitRequest;

    @Test
    void exceptionOccuredWhileContactingApi_thenReturnEmptyResponse() {
        var url = "https://api.weatherbit.io/v2.0/current/airquality?lat=1&lon=2&key=e24637b4763e45d8a8b83975cc282ca7";
        Mockito.when(restTemplate.getForObject(url, WeatherBitAPIResponse.class)).thenThrow(
                new RestClientException("Api went to china and doesn't respond.")
        );

        Assertions.assertThat(
                weatherBitRequest.getCurrentDataFromAPI("1", "2").empty()
        ).isTrue();

        Mockito.verify(restTemplate, Mockito.times(1)).getForObject(
                url, WeatherBitAPIResponse.class
        );
    }
}