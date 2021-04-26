package lei.tqs.aeolus.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/airquality")
    @ResponseBody
    public String getAirQuality(@RequestParam String latitude, @RequestParam String longitude) {
        System.out.println(latitude);
        System.out.println(longitude);
        return "air_quality.html";
    }

    @GetMapping("/error")
    public String error() {
        return "error.html";
    }
}
