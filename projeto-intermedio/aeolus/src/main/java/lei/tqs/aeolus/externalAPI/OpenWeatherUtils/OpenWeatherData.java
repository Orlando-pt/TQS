package lei.tqs.aeolus.ExternalAPI.OpenWeatherUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Calendar;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherData {
    private OpenWeatherMeasure components;
    private long dt;
}
