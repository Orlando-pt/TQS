package lei.tqs.aeolus.external_api;

import lei.tqs.aeolus.external_api.weather_bit_utils.WeatherBitAPIResponse;
import lei.tqs.aeolus.external_api.weather_bit_utils.WeatherBitRequest;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

public class WeatherBitAPI implements ExternalApiInterface{

    private WeatherBitRequest weatherBitRequest;

    public WeatherBitAPI(WeatherBitRequest weatherBitRequest) { this.weatherBitRequest = weatherBitRequest; }

    @Override
    public APIResponse getCurrentAQ(String lat, String lng) {
        return this.parseApiResponseToCommonResponse(
                this.weatherBitRequest.getCurrentDataFromAPI(lat, lng)
        );
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

    private APIResponse parseApiResponseToCommonResponse(WeatherBitAPIResponse response) {
        var apiResponse = new APIResponse();

        /**
         * if response is empty, return also a empty apiResponse
         */
        if (response.empty())
            return apiResponse;

        // set coordinates
        apiResponse.setLatitude(response.getLat());
        apiResponse.setLongitude(response.getLon());

        // add measures
        response.getData().forEach( (measure) -> {
            apiResponse.addMeasure(
                    new Measure(
                            measure.getCo(),
                            measure.getNo2(),
                            measure.getO3(),
                            measure.getSo2(),
                            measure.getPm10()
                    )
            );
        });

        return apiResponse;
    }

}
