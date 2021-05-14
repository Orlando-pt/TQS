package lei.tqs.aeolus.rest_controllers;

import lei.tqs.aeolus.external_api.APIResponse;
import lei.tqs.aeolus.services.AqServiceInterface;
import lei.tqs.aeolus.utils.GeneralUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.*;

@Log4j2
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestControllerAirQualityTemplateIT {

    @LocalServerPort
    private int serverPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AqServiceInterface service;

    private HttpEntity requestEntity;
    private final String[] coord = {"40.640632", "-8.653754"};

    @BeforeEach
    void setUp() {
        var httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        this.requestEntity = new HttpEntity<>(httpHeaders);
    }

    @AfterEach
    void tearDown() {
        this.service.resetCache();
    }

    @Test
    void whenNewLocationIsRequested_ThenTheLocationShouldBeStoredOnCache() {
        var response = this.restTemplate.exchange(
                "/api/aq/currentaq?lat=" + this.coord[0] + "&lng=" + this.coord[1]
                , HttpMethod.GET, this.requestEntity, APIResponse.class
        );
        log.info(response);

        Assertions.assertThat(
                response.getStatusCode()
        ).isEqualTo(HttpStatus.OK);

        // check if the latitude remains correct
        Assertions.assertThat(
                response.getBody().getLatitude()
        ).isEqualTo("40.6406");

        // verify if the current air quality list has one element
        Assertions.assertThat(
                response.getBody().getMeasureList()
        ).hasSize(1);

        // verify if the coordinate measure was stored on cache
        var cacheResponse = this.restTemplate.exchange(
                "/api/cache/cachecontains?lat=" + this.coord[0] +
                        "&lng=" + this.coord[1], HttpMethod.GET, this.requestEntity, Boolean.class
        );

        Assertions.assertThat(
            cacheResponse.getBody()
        ).isTrue();
    }

    @Test
    void verifyIfHistoryAirQuality_returnMoreThanOneMeasure() {
        // get measures from the last 2 days
        var response = this.restTemplate.exchange(
                "/api/aq/history?lat=" + this.coord[0] +
                        "&lng=" + this.coord[1] + "&days=2",
                HttpMethod.GET,
                this.requestEntity, APIResponse.class
        );

        Assertions.assertThat(
                response.getStatusCode()
        ).isEqualTo(HttpStatus.OK);

        // check if the latitude remains correct
        Assertions.assertThat(
                response.getBody().getLatitude()
        ).isEqualTo("40.6406");

        // last 2 days, it means that the measureList should have 49 measures (one each hour + 1)
        Assertions.assertThat(
                response.getBody().getMeasureList()
        ).hasSize(49);
    }

    @Test
    void verifyMeasuresOfTheDayBefore() {
        var yesterday = GeneralUtils.returnCalendarLastHourTimeUnix();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        var month = yesterday.get(Calendar.MONTH) + 1;

        // get measures from yesterday
        var response = this.restTemplate.exchange(
                "/api/aq/fromday?lat=" + this.coord[0] +
                        "&lng=" + this.coord[1] +
                "&year=" + yesterday.get(Calendar.YEAR) +
                        "&month=" + month +
                "&day=" + yesterday.get(Calendar.DAY_OF_MONTH) +
                "&hour=" + yesterday.get(Calendar.HOUR_OF_DAY),
                HttpMethod.GET,
                this.requestEntity, APIResponse.class
        );

        log.info(response);

        Assertions.assertThat(
                response.getStatusCode()
        ).isEqualTo(HttpStatus.OK);

        // check if the latitude is correct
        Assertions.assertThat(
                response.getBody().getLatitude()
        ).isEqualTo("40.6406");

        // measures from yesterday, it means that the measureList should have 25 measures (one each hour + 1)
        Assertions.assertThat(
                response.getBody().getMeasureList()
        ).hasSize(25);
    }

    @Test
    void makeRequestsAndVerifyCacheStatsTest() {
        this.makeRequests();
        /**
         * this method will simulate requests
         * 5 for Aveiro ( 1 will be responded from the api, the other 4 from cache)
         * 2 for Ovar ( 1 -> api, and another answered by cache)
         * 3 for Porto (1 -> api, 2 from the cache)
         * cache size == 3
         * requests made == 10
         * requests answered with success == 7
         * percentage of successful requests == 70.0
         */

        Assertions.assertThat(
                this.checkStatCacheIntegers("/api/cache/size").getBody()
        ).isEqualTo(3);

        // check cached locations
        var coords = this.mapWithCoordinates();
        Assertions.assertThat(
                this.restTemplate.exchange(
                        "/api/cache/cachedlocations",
                        HttpMethod.GET,
                        this.requestEntity,
                        new ParameterizedTypeReference<List<Map<String, String>>>() {
                        }
                ).getBody()
        ).contains(coords.get(0), coords.get(1), coords.get(2));

        // verify if cache contains Aveiro
        Assertions.assertThat(
                this.checkStatCacheBoolean("/api/cache/cacherequestsonlocation" +
                        "?lat=" + this.coord[0] + "&lng=" + this.coord[1]).getBody()
        ).isTrue();

        Assertions.assertThat(
                this.checkStatCacheLong("/api/cache/cacherequestsasked").getBody()
        ).isEqualTo(10L);

        Assertions.assertThat(
                this.checkStatCacheLong("/api/cache/cacherequestsanswered").getBody()
        ).isEqualTo(7L);

        // check that Aveiro was requested 5 times
        Assertions.assertThat(
                this.checkStatCacheLong("/api/cache/cacherequestsonlocation" +
                        "?lat=" + this.coord[0] + "&lng=" + this.coord[1]).getBody()
        ).isEqualTo(5L);

        // most requested location (Aveiro with 5 requests
        Assertions.assertThat(
                this.restTemplate.exchange(
                        "/api/cache/cachemostrequestedlocation",
                        HttpMethod.GET,
                        this.requestEntity,
                        Object.class
                ).getBody().toString()
        ).isEqualTo("{40.6406=-8.6537}");

        // percentage of successful requests
        Assertions.assertThat(
                this.restTemplate.exchange(
                        "/api/cache/cachepercentagesuccessfulrequests",
                        HttpMethod.GET,
                        this.requestEntity,
                        Double.class
                ).getBody()
        ).isEqualTo(70.0);

    }

    private HttpEntity checkStatCacheIntegers(String url) {
        return this.restTemplate.exchange(
                url, HttpMethod.GET, this.requestEntity, Integer.class
        );
    }

    private HttpEntity checkStatCacheLong(String url) {
        return this.restTemplate.exchange(
                url, HttpMethod.GET, this.requestEntity, Long.class
        );
    }

    private HttpEntity<Boolean> checkStatCacheBoolean(String url) {
        return this.restTemplate.exchange(
                url, HttpMethod.GET, this.requestEntity, Boolean.class
        );
    }

    private List<Map> mapWithCoordinates() {
        var ret = new ArrayList<Map>(3);
        var m1 = new HashMap<String, String>(1);
        m1.put("40.6406", "-8.6537");
        ret.add(m1);

        var m2 = new HashMap<String, String>(1);
        m2.put("40.8642", "-8.6179");
        ret.add(m2);

        var m3 = new HashMap<String, String>(1);
        m3.put("41.1623", "-8.6335");
        ret.add(m3);
        return ret;
    }

    private void makeRequests() {
        /**
         * simulate requests to specific locations
         */
        List<String[]> coordinates = Arrays.asList(
                this.coord,
                new String[]{"40.864219838791676", "-8.617973844878795"},
                new String[]{"41.16239830874021", "-8.63354823000507"}
        );

        // requests for each one of the coordinates
        var requests = new Integer[]{5, 2, 3};

        // make requests
        for(var i = 0; i < 3; i++) {
            for (var req = 0; req < requests[i]; req++) {
                this.restTemplate.exchange(
                        "/api/aq/currentaq?lat=" + coordinates.get(i)[0] + "&lng=" + coordinates.get(i)[1]
                        , HttpMethod.GET, this.requestEntity, APIResponse.class
                );
            }
        }
    }
}
