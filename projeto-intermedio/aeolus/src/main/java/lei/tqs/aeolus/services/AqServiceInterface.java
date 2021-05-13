package lei.tqs.aeolus.services;

import lei.tqs.aeolus.external_api.APIResponse;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Calendar;
import java.util.Set;

public interface AqServiceInterface {
    // retrive information from api's
    APIResponse getCurrentAQ(String lat, String lng);
    APIResponse getHistoryAQPreviousDays(String lat, String lng, int days);
    APIResponse getHistoryAQByDayAndHourUntilPresent(String lat, String lng, Calendar day);
    APIResponse getHistoryAQBetweenDays(String lat, String lng, Calendar initial, Calendar end);


    // information about cache
    int cacheSize();
    Set<ImmutablePair<String, String>> getCachedLocations();
    boolean cacheIsFull();
    int cacheMaxSize();
    boolean cacheContainsLocation(ImmutablePair<String, String> location);
    long cacheRequestsAsked();
    long cacheRequestsAnswered();
    long cacheRequestsToLocation(ImmutablePair<String, String> location);
    ImmutablePair<String, String> cacheMostRequestedLocation();
    double cachePercentageSuccessfulRequestsAnswered();
}
