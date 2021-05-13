package lei.tqs.aeolus.services;

import lei.tqs.aeolus.cache.Cache;
import lei.tqs.aeolus.external_api.APIResponse;
import lei.tqs.aeolus.external_api.OpenWeatherAPI;
import lei.tqs.aeolus.external_api.WeatherBitAPI;
import lei.tqs.aeolus.external_api.open_weather_utils.OpenWeatherRequest;
import lei.tqs.aeolus.external_api.weather_bit_utils.WeatherBitRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Set;

@Log4j2
@Service
public class AqService implements AqServiceInterface{

    private Cache<ImmutablePair<String, String>, APIResponse> cache;;
    private OpenWeatherAPI openWeatherAPI;
    private WeatherBitAPI weatherBitAPI;

//    public AqService() {
//        this.cache = new Cache<>(300, 120, 10000);
//        this.openWeatherAPI = new OpenWeatherAPI(new OpenWeatherRequest());
//        this.weatherBitAPI = new WeatherBitAPI(new WeatherBitRequest());
//    }
//
//    public AqService(Cache cache,OpenWeatherAPI openWeatherAPI, WeatherBitAPI weatherBitAPI) {
//        this.cache = cache;
//        this.openWeatherAPI = openWeatherAPI;
//        this.weatherBitAPI = weatherBitAPI;
//    }

    public void setCache(Cache cache) {this.cache = cache; }
    public void setOpenWeatherAPI(OpenWeatherAPI openWeatherAPI) {this.openWeatherAPI = openWeatherAPI; }
    public void setWeatherBitAPI(WeatherBitAPI weatherBitAPI) {this.weatherBitAPI = weatherBitAPI; }

    @Override
    public APIResponse getCurrentAQ(String lat, String lng) {
        log.info("Getting current air quality on location (" + lat + "," + lng + ")");
        var location = ImmutablePair.of(lat, lng);

        // return APIResponse if location is already on cache
        var result = this.cache.get(location);
        if (result.isPresent())
            return result.get();

        // if not, request air quality to openweather api

        // if error, request air quality to weatherbit api

        // no response, return empty APIResponse
        return new APIResponse();
    }

    @Override
    public APIResponse getHistoryAQPreviousDays(String lat, String lng, int days) {
        return null;
    }

    @Override
    public APIResponse getHistoryAQByDayAndHourUntilPresent(String lat, String lng, Calendar day) {
        return null;
    }

    @Override
    public APIResponse getHistoryAQBetweenDays(String lat, String lng, Calendar initial, Calendar end) {
        return null;
    }

    @Override
    public int cacheSize() {
        return 0;
    }

    @Override
    public Set<ImmutablePair<String, String>> getCachedLocations() {
        return null;
    }

    @Override
    public boolean cacheIsFull() {
        return false;
    }

    @Override
    public int cacheMaxSize() {
        return 0;
    }

    @Override
    public boolean cacheContainsLocation(ImmutablePair<String, String> location) {
        return false;
    }

    @Override
    public long cacheRequestsAsked() {
        return 0;
    }

    @Override
    public long cacheRequestsAnswered() {
        return 0;
    }

    @Override
    public long cacheRequestsToLocation(ImmutablePair<String, String> location) {
        return 0;
    }

    @Override
    public ImmutablePair<String, String> cacheMostRequestedLocation() {
        return null;
    }

    @Override
    public double cachePercentageSuccessfulRequestsAnswered() {
        return 0;
    }
}
