package lei.tqs.aeolus.rest_controllers;

import lei.tqs.aeolus.services.AqServiceInterface;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Log4j2
@RestController
@RequestMapping("/api/cache")
public class RestControllerCache {

    @Autowired
    private AqServiceInterface aqService;

    @GetMapping("/size")
    public Integer getCacheCurrentSize() {
        log.info("Request at: /api/cache/size");
        return this.aqService.cacheSize();
    }

    @GetMapping("/cachedlocations")
    public Set<ImmutablePair<String, String>> getCachedLocations() {
        log.info("Request at: /api/cache/cachedlocations");
        return this.aqService.getCachedLocations();
    }

    @GetMapping("/cachefull")
    public Boolean getCacheIsFull() {
        log.info("Request at: /api/cache/cachefull");
        return this.aqService.cacheIsFull();
    }

    @GetMapping("/cachemaxsize")
    public Integer getCacheMaxSize() {
        log.info("Request at: /api/cache/cachemaxsize");
        return this.aqService.cacheMaxSize();
    }

    @GetMapping("/cachecontains")
    @ResponseBody
    public Boolean getCacheContainsLocation(@RequestParam String lat, @RequestParam String lng) {
        log.info("Request at: /api/cache/cachecontains");
        return this.aqService.cacheContainsLocation(ImmutablePair.of(lat, lng));
    }

    @GetMapping("/cacherequestsasked")
    public Long getCacheRequestsAsked() {
        log.info("Request at: /api/cache/cacherequestsasked");
        return this.aqService.cacheRequestsAsked();
    }

    @GetMapping("/cacherequestsanswered")
    public Long getCacheRequestsAnswered() {
        log.info("Request at: /api/cache/cacherequestsanswered");
        return this.aqService.cacheRequestsAnswered();
    }

    @GetMapping("/cacherequestsonlocation")
    @ResponseBody
    public Long getCacheRequestsToLocation(@RequestParam String lat, @RequestParam String lng) {
        log.info("Request at: /api/cache/cacherequestsonlocation");
        return this.aqService.cacheRequestsToLocation(ImmutablePair.of(lat, lng));
    }

    @GetMapping("/cachemostrequestedlocation")
    public ImmutablePair<String, String> getCacheMostRequestedLocation() {
        log.info("Request at: /api/cache/cachemostrequestedlocation");
        return this.aqService.cacheMostRequestedLocation();
    }

    @GetMapping("/cachepercentagesuccessfulrequests")
    public Double getCachePercentageSuccessfulRequests() {
        log.info("Request at: /api/cache/cachepercentagesuccessfulrequests");
        return this.aqService.cachePercentageSuccessfulRequestsAnswered();
    }
}
