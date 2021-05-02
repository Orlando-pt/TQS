package lei.tqs.aeolus.external_api;

import lei.tqs.aeolus.external_api.open_weather_utils.OpenWeatherResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

public class OpenWeatherAPI implements ExternalApiInterface{
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

    public OpenWeatherAPI() {
        this.restTemplate = new RestTemplate();
    }

    public OpenWeatherResponse getCurrentAQFromAPI(String lat, String lng) {
        String url = URL_CURRENT_AQ + lat + "&lon=" + lng + "&appid=" + API_KEY;

        return this.restTemplate.getForObject(url, OpenWeatherResponse.class);
    }

    public OpenWeatherResponse getHistoryAQFromAPI(String lat, String lng) {
        var current = 1619456400;
        int start = 1619456400 - 3600;
        String url = URL_HISTORY_AQ + lat + "&lon=" + lng + "&start=" + start + "&end=" + current + "&appid=" + API_KEY;

        return this.restTemplate.getForObject(url, OpenWeatherResponse.class);
    }

    @Override
    public APIResponse getCurrentAQ(String lat, String lng) {
        return null;
    }

    @Override
    public APIResponse getHistoryAQPreviousDays(String lat, String lng, int days) {
        return null;
    }

    @Override
    public APIResponse getHistoryAQByDayAndHourUntilPresent(String lat, String lng, Date day, int hour) {
        return null;
    }

    @Override
    public APIResponse getHistoryAQBetweenDays(String lat, String lng, Date initial, Date end) {
        return null;
    }

    @Override
    public APIResponse getHistoryAQBetweenHours(String lat, String lng, Date day, int initial, int end) {
        return null;
    }

    @Override
    public APIResponse getHistoryAQBetweenDaysWithHours(String lat, String lng, Date initial, Date end, int initialHour, int finalHour) {
        return null;
    }

}
