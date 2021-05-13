package lei.tqs.aeolus.services;

import lei.tqs.aeolus.cache.Cache;
import lei.tqs.aeolus.external_api.APIResponse;
import lei.tqs.aeolus.external_api.OpenWeatherAPI;
import lei.tqs.aeolus.external_api.WeatherBitAPI;
import lei.tqs.aeolus.external_api.open_weather_utils.OpenWeatherRequest;
import lei.tqs.aeolus.external_api.weather_bit_utils.WeatherBitRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Set;

@Log4j2
@Service
public class AqService implements AqServiceInterface{

    private Cache<ImmutablePair<String, String>, APIResponse> cache;
    private OpenWeatherAPI openWeatherAPI;
    private WeatherBitAPI weatherBitAPI;

    public AqService() {
        this.cache = new Cache<>(300, 120, 10000);
        this.openWeatherAPI = new OpenWeatherAPI(new OpenWeatherRequest());
        this.weatherBitAPI = new WeatherBitAPI(new WeatherBitRequest());
    }

    @Override
    public APIResponse getCurrentAQ(String lat, String lng) {
        if (!this.correctCoordinates(lat, lng))
            return new APIResponse();

        log.info("Getting current air quality on location (" + lat + "," + lng + ")");
        var location = ImmutablePair.of(lat, lng);

        // return APIResponse if location is already on cache
        var result = this.cache.get(location);
        if (result.isPresent())
            return result.get();

        // if not, request air quality to openweather api
        var openWeatherResponse = this.openWeatherAPI.getCurrentAQ(lat, lng);
        if (!openWeatherResponse.empty())
            return openWeatherResponse;

        // if error, request air quality to weatherbit api
        var weatherBitResponse = this.weatherBitAPI.getCurrentAQ(lat, lng);
        if (!weatherBitResponse.empty()) {
            return weatherBitResponse;
        }

        // no response, return empty APIResponse
        return new APIResponse();
    }

    @Override
    public APIResponse getHistoryAQPreviousDays(String lat, String lng, int days) {
        if (!this.correctCoordinates(lat, lng))
            return new APIResponse();

        log.info("Getting history air quality on location (" + lat + "," + lng + ")");
        var openWeatherResponse = this.openWeatherAPI.getHistoryAQPreviousDays(
                lat, lng, days);
        if (!openWeatherResponse.empty())
            return openWeatherResponse;

        var weatherBitResponse = this.weatherBitAPI.getHistoryAQPreviousDays(
                lat, lng, days);
        if (!weatherBitResponse.empty())
            return weatherBitResponse;

        return new APIResponse();
    }

    @Override
    public APIResponse getHistoryAQByDayAndHourUntilPresent(String lat, String lng, Calendar day) {
        if (!this.correctCoordinates(lat, lng))
            return new APIResponse();

        log.info("Getting history air quality since " + day.getTime() +
                " on location (" + lat + "," + lng + ")");
        var openWeatherResponse = this.openWeatherAPI.getHistoryAQByDayAndHourUntilPresent(
                lat, lng, day);
        if (!openWeatherResponse.empty())
            return openWeatherResponse;

        var weatherBitResponse = this.weatherBitAPI.getHistoryAQByDayAndHourUntilPresent(
                lat, lng, day);
        if (!weatherBitResponse.empty())
            return weatherBitResponse;

        return new APIResponse();
    }

    @Override
    public APIResponse getHistoryAQBetweenDays(String lat, String lng, Calendar initial, Calendar end) {
        if (!this.correctCoordinates(lat, lng))
            return new APIResponse();

        log.info("Getting history air quality since " + initial.getTime() +
                " until " + end.getTime() +
                " on location (" + lat + "," + lng + ")");
        var openWeatherResponse = this.openWeatherAPI.getHistoryAQBetweenDays(
                lat, lng, initial, end);
        if (!openWeatherResponse.empty())
            return openWeatherResponse;

        var weatherBitResponse = this.weatherBitAPI.getHistoryAQBetweenDays(
                lat, lng, initial, end);
        if (!weatherBitResponse.empty())
            return weatherBitResponse;

        return new APIResponse();
    }

    @Override
    public int cacheSize() {
        log.info("Request checking cache size");
        return this.cache.size();
    }

    @Override
    public Set<ImmutablePair<String, String>> getCachedLocations() {
        log.info("Requesting keys stored on cache");
        return this.cache.getKeys();
    }

    @Override
    public boolean cacheIsFull() {
        log.info("Verifying if cache is full");
        return this.cache.isFull();
    }

    @Override
    public int cacheMaxSize() {
        log.info("Requesting the max size of the cache");
        return this.cache.maxSize();
    }

    @Override
    public boolean cacheContainsLocation(ImmutablePair<String, String> location) {
        log.info("Verifying if the coordinate (" + location.getLeft() +
                "," + location.getRight() + " is stored on cache");
        return this.cache.containsKey(location);
    }

    @Override
    public long cacheRequestsAsked() {
        log.info("Verifying how many requests were made");
        return this.cache.numberOfRequests();
    }

    @Override
    public long cacheRequestsAnswered() {
        log.info("Verifying how many requests were answered successfully");
        return this.cache.requestsAnswered();
    }

    @Override
    public long cacheRequestsToLocation(ImmutablePair<String, String> location) {
        log.info("Verifying how many requests were made with the coordinate (" + location.getLeft() +
                "," + location.getRight() + " stored on cache");

        return this.cache.requestsToKey(location);
    }

    @Override
    public ImmutablePair<String, String> cacheMostRequestedLocation() {
        log.info("Retrieving the most requested location");
        return this.cache.mostRequested();
    }

    @Override
    public double cachePercentageSuccessfulRequestsAnswered() {
        log.info("Checking the percentage of requests successfully answered");
        return this.cache.percentageOfSuccessfulRequestsAnswered();
    }

    /**
     * verify if the coordinates are correct
     * @param lat latitude
     * @param lng longitude
     * @return  true if coordinates are correct
     */
    private boolean correctCoordinates(String lat, String lng) {
        try {
            if (Math.abs(Double.parseDouble(lat)) <= 90 &&
                    Math.abs(Double.parseDouble(lng)) <= 180)
                return true;
        } catch (Exception e) {
            log.error(e);
        }
        log.info("Bad coordinates requested. (" + lat + "," + lng + ")");
        return false;
    }
}
