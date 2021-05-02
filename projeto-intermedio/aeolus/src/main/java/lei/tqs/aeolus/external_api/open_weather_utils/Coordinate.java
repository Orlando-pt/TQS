package lei.tqs.aeolus.external_api.open_weather_utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)

public class Coordinate {
    private String lon;
    private String lat;
}
