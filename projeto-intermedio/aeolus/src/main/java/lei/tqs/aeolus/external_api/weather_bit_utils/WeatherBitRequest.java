package lei.tqs.aeolus.external_api.weather_bit_utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Log4j2
public class WeatherBitRequest {

    /**
     * current:
     * curl -X GET --header 'Accept: application/json' 'https://api.weatherbit.io/v2.0/current/airquality?lat=41.16225097228622&lon=-8.628822176072289&key=e24637b4763e45d8a8b83975cc282ca7'
     *
     * history:
     *
     * curl -X GET --header 'Accept: application/json' 'https://api.weatherbit.io/v2.0/history/airquality?lat=40.866057889889206&lon=-8.645710577339893&key=e24637b4763e45d8a8b83975cc282ca7'
     * ambos atualizam de hora a hora, thats noice
     * @param lat
     * @param lng
     * @return
     */

    /**
     * Error handling information
     * By default, the RestTemplate will throw one of these exceptions in case of an HTTP error:
     *
     *     HttpClientErrorException – in case of HTTP status 4xx
     *     HttpServerErrorException – in case of HTTP status 5xx
     *     UnknownHttpStatusCodeException – in case of an unknown HTTP status
     *
     * All these exceptions are extensions of RestClientResponseException.
     */

    private static final String API_KEY = "e24637b4763e45d8a8b83975cc282ca7";
    private static final String URL_CURRENT_AQ = "https://api.weatherbit.io/v2.0/current/airquality?lat=";
    private static final String URL_HISTORY_AQ = "https://api.weatherbit.io/v2.0/history/airquality?lat=";

    private RestTemplate restTemplate;

    public WeatherBitRequest() {
        this.restTemplate = new RestTemplate();
    }

    public WeatherBitAPIResponse getCurrentDataFromAPI(String lat, String lng) {
        String url = URL_CURRENT_AQ + lat + "&lon=" + lng + "&key=" + API_KEY;
        var weatherBitResponse = new WeatherBitAPIResponse();

        log.info("Making request to WeatherBit api. Url : " + url);

        try {
            weatherBitResponse = this.restTemplate.getForObject(url, WeatherBitAPIResponse.class);
        } catch (RestClientException e) {
            log.error(e);
        }

        return weatherBitResponse;
    }

    public WeatherBitAPIResponse getHistoryDataFromAPI(String lat, String lng) {
        String url = URL_HISTORY_AQ + lat + "&lon=" + lng + "&key=" + API_KEY;

        var weatherBitResponse = new WeatherBitAPIResponse();
        log.info("Making request to WeatherBit api. Url : " + url);

        try {
            weatherBitResponse = this.restTemplate.getForObject(url, WeatherBitAPIResponse.class);
        } catch (RestClientException e) {
            log.error(e);
        }

        return weatherBitResponse;
    }
}
