package lei.tqs.aeolus.external_api.open_weather_utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherMeasure {
    private float co;
    private float no2;
    private float o3;
    private float so2;
    private float pm10;
}
