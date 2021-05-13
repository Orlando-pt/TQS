package lei.tqs.aeolus.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Log4j2
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/airquality")
    @ResponseBody
    public String getAirQuality(@RequestParam String latitude, @RequestParam String longitude) {
        log.info("Received latitude: " + latitude);
        log.info("Received longitude: " + longitude);
        return "air_quality.html";
    }

    @GetMapping("/error")
    public String error() {
        return "error.html";
    }
}
