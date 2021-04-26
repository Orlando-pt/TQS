package lei.tqs.aeolus.ExternalAPI.WeatherBitUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Calendar;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WeatherBitMeasure {
    private float co;
    private float no2;
    private float o3;
    private float so2;
    private float pm10;
    private Calendar timestamp_utc;
}
