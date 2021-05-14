package lei.tqs.aeolus.external_api;

import lei.tqs.aeolus.external_api.open_weather_utils.OpenWeatherRequest;
import lei.tqs.aeolus.external_api.open_weather_utils.OpenWeatherResponse;
import lei.tqs.aeolus.utils.GeneralUtils;
import lombok.extern.log4j.Log4j2;

import java.util.Calendar;

@Log4j2
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
        return parseApiResponseToCommonResponse(lat, lng,
                this.openWeatherRequest.getCurrentAQFromAPI(lat, lng)
        );
    }

    @Override
    public APIResponse getHistoryAQPreviousDays(String lat, String lng, int days) {
        return this.parseApiResponseToCommonResponse(lat, lng,
                this.openWeatherRequest.getHistoryAQFromAPI(lat, lng, days)
        );
    }

    @Override
    public APIResponse getHistoryAQByDayAndHourUntilPresent(String lat, String lng, Calendar day) {
        var end = GeneralUtils.returnCalendarLastHourTimeUnix().getTimeInMillis() / 1000L;
        return this.parseApiResponseToCommonResponse(lat, lng,
                this.openWeatherRequest.getHistoryAQFromAPIStartEnd(
                        lat,
                        lng,
                        day.getTimeInMillis() / 1000L,
                        end
                )
        );
    }

    @Override
    public APIResponse getHistoryAQBetweenDays(String lat, String lng, Calendar initial, Calendar end) {
        return this.parseApiResponseToCommonResponse(lat, lng,
                this.openWeatherRequest.getHistoryAQFromAPIStartEnd(
                        lat,
                        lng,
                        initial.getTimeInMillis() / 1000L,
                        end.getTimeInMillis() / 1000L
                )
        );
    }

    private APIResponse parseApiResponseToCommonResponse(
            String lat, String lng, OpenWeatherResponse response) {
        APIResponse apiResponse = new APIResponse();

        /**
         * if response is empty, return also a empty apiResponse
         */
        if (response.empty())
            return apiResponse;

        // set coordinates
        apiResponse.setLatitude(lat);
        apiResponse.setLongitude(lng);

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
