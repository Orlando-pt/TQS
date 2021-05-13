package lei.tqs.aeolus.rest_controllers;

import lei.tqs.aeolus.services.AqServiceInterface;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        log.info("Request at: /api/cache/size");
        return this.aqService.getCachedLocations();
    }
}
