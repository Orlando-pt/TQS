package lei.tqs.aeolus.ExternalAPI;

import lei.tqs.aeolus.ExternalAPI.WeatherBitUtils.WeatherBitAPIResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

public class WeatherBitAPI implements ExternalApiInterface{

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

    private static final String API_KEY = "e24637b4763e45d8a8b83975cc282ca7";
    private static final String URL_CURRENT_AQ = "https://api.weatherbit.io/v2.0/current/airquality?lat=";
    private static final String URL_HISTORY_AQ = "https://api.weatherbit.io/v2.0/history/airquality?lat=";
    private RestTemplate restTemplate;
    public WeatherBitAPI() {
        this.restTemplate = new RestTemplate();
    }

    public WeatherBitAPIResponse getCurrentDataFromAPI(String lat, String lng) {
        String url = URL_CURRENT_AQ + lat + "&lon=" + lng + "&key=" + API_KEY;

        return this.restTemplate.getForObject(url, WeatherBitAPIResponse.class);
    }

    public WeatherBitAPIResponse getHistoryDataFromAPI(String lat, String lng) {
        String url = URL_HISTORY_AQ + lat + "&lon=" + lng + "&key=" + API_KEY;

        return this.restTemplate.getForObject(url, WeatherBitAPIResponse.class);
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
