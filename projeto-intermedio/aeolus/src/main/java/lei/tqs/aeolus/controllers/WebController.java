package lei.tqs.aeolus.controllers;

import lei.tqs.aeolus.services.AqServiceInterface;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Log4j2
public class WebController {

    @Autowired
    private AqServiceInterface aqService;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/currentairquality")
    public String getAirQuality(
            @RequestParam String latitude,
            @RequestParam String longitude,
            @RequestParam String country,
            @RequestParam String city,
            Model model) {
        model.addAttribute("title", "Current air quality in " + city);

        model.addAttribute("response", this.aqService.getCurrentAQ(latitude, longitude));
        return "air_quality.html";
    }

    @GetMapping("/historyairquality")
    public String getAirQualityHistory(
            @RequestParam String lat,
            @RequestParam String lng,
            @RequestParam String country,
            @RequestParam String city,
            @RequestParam Integer days,
            Model model
    ) {
    model.addAttribute("title", "History air quality in " + city);
    model.addAttribute("response", this.aqService.getHistoryAQPreviousDays(
            lat, lng, days
    ));
    return "air_quality.html";
    }

    @GetMapping("/error")
    public String error() {
        return "error.html";
    }
}
