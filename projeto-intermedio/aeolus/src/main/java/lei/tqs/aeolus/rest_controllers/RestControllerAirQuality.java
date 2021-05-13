package lei.tqs.aeolus.rest_controllers;

import lei.tqs.aeolus.external_api.APIResponse;
import lei.tqs.aeolus.services.AqServiceInterface;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/aq")
public class RestControllerAirQuality {

    @Autowired
    private AqServiceInterface aqService;

    @GetMapping("/currentaq")
    @ResponseBody
    public APIResponse getCurrentAirQuality(@RequestParam String lat, @RequestParam String lng) {
        return this.aqService.getCurrentAQ(lat, lng);
    }
}
