package lei.tqs.aeolus.ExternalAPI.WeatherBitUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WeatherBitAPIResponse {

    private List<WeatherBitMeasure> data;
    private String city_name;
    private String lon;
    private String timezone;
    private String lat;
    private String country_code;
    private String state_code;
}
