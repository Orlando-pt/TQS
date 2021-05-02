package lei.tqs.aeolus.external_api.open_weather_utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Setter @Getter @NoArgsConstructor
@AllArgsConstructor @ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherResponse {
    private Coordinate coord;
    private List<OpenWeatherData> list;
}
