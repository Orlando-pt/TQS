package lei.tqs.aeolus.external_api;

import lei.tqs.aeolus.external_api.weather_bit_utils.WeatherBitAPIResponse;
import lei.tqs.aeolus.external_api.weather_bit_utils.WeatherBitRequest;
import lombok.extern.log4j.Log4j2;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.stream.Collectors;

@Log4j2
public class WeatherBitAPI implements ExternalApiInterface{

    private WeatherBitRequest weatherBitRequest;

    public WeatherBitAPI(WeatherBitRequest weatherBitRequest) { this.weatherBitRequest = weatherBitRequest; }

    @Override
    public APIResponse getCurrentAQ(String lat, String lng) {
        return this.parseApiResponseToCommonResponse(lat, lng,
                this.weatherBitRequest.getCurrentDataFromAPI(lat, lng)
        );
    }

    @Override
    public APIResponse getHistoryAQPreviousDays(String lat, String lng, int days) {
        if (days > 3) {
            log.error("WeatherBit api only saves records for 3 days.");
            return new APIResponse();
        }

        var minCalendar = Calendar.getInstance();
        minCalendar.add(Calendar.DAY_OF_MONTH, -days);

        WeatherBitAPIResponse weatherBitAPIResponse = this.weatherBitRequest.getHistoryDataFromAPI(lat, lng);

        // filter measures
        var measures = weatherBitAPIResponse.getData().stream()
                .filter((measure) -> measure.getTimestamp_local().getTimeInMillis()
                        >= minCalendar.getTimeInMillis())
                .collect(Collectors.toList());

        weatherBitAPIResponse.setData(measures);

        return this.parseApiResponseToCommonResponse(lat, lng,
            weatherBitAPIResponse
        );
    }

    @Override
    public APIResponse getHistoryAQByDayAndHourUntilPresent(String lat, String lng, Calendar day) {
        var today = Calendar.getInstance();
        var difference = ChronoUnit.DAYS.between(today.toInstant(), day.toInstant());

        if (Math.abs(difference) > 3L) {
            log.error("WeatherBit api only saves records for 3 days.");
            return new APIResponse();
        }

        WeatherBitAPIResponse weatherBitAPIResponse = this.weatherBitRequest.getHistoryDataFromAPI(lat, lng);

        // filter measures
        var measures = weatherBitAPIResponse.getData().stream()
                .filter((measure) -> measure.getTimestamp_local().getTimeInMillis()
                        >= day.getTimeInMillis())
                .collect(Collectors.toList());

        weatherBitAPIResponse.setData(measures);

        return this.parseApiResponseToCommonResponse(lat, lng,
                weatherBitAPIResponse
        );
    }

    @Override
    public APIResponse getHistoryAQBetweenDays(String lat, String lng, Calendar initial, Calendar end) {
        var today = Calendar.getInstance();
        var difference = ChronoUnit.DAYS.between(today.toInstant(), initial.toInstant());

        if (Math.abs(difference) > 3L) {
            log.error("WeatherBit api only saves records for 3 days.");
            return new APIResponse();
        }

        WeatherBitAPIResponse weatherBitAPIResponse = this.weatherBitRequest.getHistoryDataFromAPI(lat, lng);

        log.debug(weatherBitAPIResponse);
        // filter measures
        var measures = weatherBitAPIResponse.getData().stream()
                .filter((measure) ->
                        measure.getTimestamp_local().getTimeInMillis() >= initial.getTimeInMillis() &&
                        measure.getTimestamp_local().getTimeInMillis() <= end.getTimeInMillis()
                )
                .collect(Collectors.toList());

        weatherBitAPIResponse.setData(measures);

        return this.parseApiResponseToCommonResponse(lat, lng,
                weatherBitAPIResponse
        );
    }

    private APIResponse parseApiResponseToCommonResponse(
            String lat, String lng, WeatherBitAPIResponse response) {
        var apiResponse = new APIResponse();

        /**
         * if response is empty, return also a empty apiResponse
         */
        if (response.empty())
            return apiResponse;

        // set coordinates
        apiResponse.setLatitude(lat);
        apiResponse.setLongitude(lng);

        // add measures
        response.getData().forEach( (measure) -> {
            apiResponse.addMeasure(
                    new Measure(
                            measure.getCo(),
                            measure.getNo2(),
                            measure.getO3(),
                            measure.getSo2(),
                            measure.getPm10(),
                            measure.getTimestamp_local() == null ?
                                    Calendar.getInstance() : measure.getTimestamp_local()
                    )
            );
        });

        return apiResponse;
    }

}
