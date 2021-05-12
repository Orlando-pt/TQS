package lei.tqs.aeolus.external_api;

import lei.tqs.aeolus.external_api.open_weather_utils.OpenWeatherRequest;
import lei.tqs.aeolus.external_api.open_weather_utils.OpenWeatherResponse;
import lei.tqs.aeolus.utils.GeneralUtils;

import java.util.Calendar;

public class OpenWeatherAPI implements ExternalApiInterface{

    /**
     * Historical data is accessible from 27th November 2020.
     * Because the other api only returns historic data until 3 days past
     * The capabilities of this api will be limited
     */

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
        return this.parseApiResponseToCommonResponse(
                this.openWeatherRequest.getHistoryAQFromAPI(lat, lng, days)
        );
    }

    @Override
    public APIResponse getHistoryAQByDayAndHourUntilPresent(String lat, String lng, Calendar day, int hour) {
        return null;
    }

    @Override
    public APIResponse getHistoryAQBetweenDays(String lat, String lng, Calendar initial, Calendar end) {
        return null;
    }

    @Override
    public APIResponse getHistoryAQBetweenHours(String lat, String lng, Calendar day, int initial, int end) {
        return null;
    }

    @Override
    public APIResponse getHistoryAQBetweenDaysWithHours(String lat, String lng, Calendar initial, Calendar end, int initialHour, int finalHour) {
        return null;
    }

    private APIResponse parseApiResponseToCommonResponse(OpenWeatherResponse response) {
        APIResponse apiResponse = new APIResponse();

        /**
         * if response is empty, return also a empty apiResponse
         */
        if (response.empty())
            return apiResponse;

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
                                    measure.getComponents().getPm10(),
                                    GeneralUtils.returnCalendarOfTimeUnix(
                                            measure.getDt()
                                    )
                            )
                    );
                }
        );

        return apiResponse;
    }


}
