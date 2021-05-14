package lei.tqs.aeolus.external_api.open_weather_utils;

import lei.tqs.aeolus.utils.GeneralUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;

@Log4j2
public class OpenWeatherRequest {
    /**
     * current:
     * curl -X GET --header 'Accept: application/json' 'http://api.openweathermap.org/data/2.5/air_pollution?lat=-8.645710577339893&lon=40.866057889889206&appid=00ae9df8c755778aea621c4543cf6b25'
     *
     * forecast:
     * curl -X GET --header 'Accept: application/json' 'http://api.openweathermap.org/data/2.5/air_pollution/forecast?lat=-8.645710577339893&lon=40.866057889889206&appid=00ae9df8c755778aea621c4543cf6b25'
     *
     * history:
     * http://api.openweathermap.org/data/2.5/air_pollution/history?lat=40.866057889889206&lon=-8.645710577339893&start=1619452800&end=1619456400&appid=00ae9df8c755778aea621c4543cf6b25
     * o start e end são o time unix, portanto basta calcular as datas anteriores até 3 dias
     * @param lat
     * @param lng
     * @return
     */
    private static final String URL_CURRENT_AQ = "http://api.openweathermap.org/data/2.5/air_pollution?lat=";
    private static final String URL_HISTORY_AQ = "http://api.openweathermap.org/data/2.5/air_pollution/history?lat=";
    private static final String API_KEY = "00ae9df8c755778aea621c4543cf6b25";

    private RestTemplate restTemplate;

    public OpenWeatherRequest() {
        this.restTemplate = new RestTemplate();
    }

    public OpenWeatherResponse getCurrentAQFromAPI(String lat, String lng) {
        if (!GeneralUtils.verifyLatitude(lat) || !GeneralUtils.verifyLongitude(lng))
            return new OpenWeatherResponse();

        String url = URL_CURRENT_AQ + lat + "&lon=" + lng + "&appid=" + API_KEY;

        log.info("Making request to OpenWeather api. Url : " + url);

        var openWeatherResponse = new OpenWeatherResponse();

        try {
            openWeatherResponse = this.restTemplate.getForObject(url, OpenWeatherResponse.class);
        } catch (RestClientException e) {
            log.error(e);
        }
        return openWeatherResponse;
    }

    public OpenWeatherResponse getHistoryAQFromAPI(String lat, String lng, int days) {
        if (!GeneralUtils.verifyLatitude(lat) || !GeneralUtils.verifyLongitude(lng))
            return new OpenWeatherResponse();
        var current = GeneralUtils.calculateCurrentDt();

        var start = GeneralUtils.returnCalendarLastHourTimeUnix();
        start.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH - days);
        var startTime = start.getTimeInMillis() / 1000L;

        String url = URL_HISTORY_AQ + lat + "&lon=" + lng + "&start=" + startTime + "&end=" + current + "&appid=" + API_KEY;

        log.info("Making request to OpenWeather api. Url : " + url);
        var openWeatherResponse = new OpenWeatherResponse();

        try {
            openWeatherResponse = this.restTemplate.getForObject(url, OpenWeatherResponse.class);
        } catch (RestClientException e) {
            log.error(e);
        }
        return openWeatherResponse;
    }

    public OpenWeatherResponse getHistoryAQFromAPIStartEnd(String lat, String lng, long start, long end) {
        if (!GeneralUtils.verifyLatitude(lat) || !GeneralUtils.verifyLongitude(lng))
            return new OpenWeatherResponse();
        String url = URL_HISTORY_AQ + lat + "&lon=" + lng + "&start=" + start + "&end=" + end + "&appid=" + API_KEY;

        log.info("Making request to OpenWeather api. Url : " + url);
        var openWeatherResponse = new OpenWeatherResponse();

        try {
            openWeatherResponse = this.restTemplate.getForObject(url, OpenWeatherResponse.class);
        } catch (RestClientException e) {
            log.error(e);
        }
        return openWeatherResponse;
    }
}
