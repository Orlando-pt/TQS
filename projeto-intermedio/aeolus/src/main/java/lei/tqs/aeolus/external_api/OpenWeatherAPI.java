package lei.tqs.aeolus.external_api;

import lei.tqs.aeolus.external_api.open_weather_utils.OpenWeatherRequest;
import lei.tqs.aeolus.external_api.open_weather_utils.OpenWeatherResponse;

import java.util.Date;

public class OpenWeatherAPI implements ExternalApiInterface{

    private OpenWeatherRequest openWeatherRequest;

    public OpenWeatherAPI(OpenWeatherRequest openWeatherRequest) {
        this.openWeatherRequest = openWeatherRequest;
    }

    @Override
    public APIResponse getCurrentAQ(String lat, String lng) {
        return parseApiResponseToCommonResponse(
                this.openWeatherRequest.getCurrentAQFromAPI(lat, lng)
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

    private APIResponse parseApiResponseToCommonResponse(OpenWeatherResponse response) {
        APIResponse apiResponse = new APIResponse();

        // set coordinates
        apiResponse.setLatitude(response.getCoord().getLat());
        apiResponse.setLongitude(response.getCoord().getLon());

        // add measures
        response.getList().forEach( (measure) -> {
            apiResponse.addMeasure(
                    new Measure(
                            measure.getComponents().getCo(),
                            measure.getComponents().getNo2(),
                            measure.getComponents().getO3(),
                            measure.getComponents().getSo2(),
                            measure.getComponents().getPm10()
                    )
            );
                }
        );

        return apiResponse;
    }

}
