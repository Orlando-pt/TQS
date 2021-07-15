package lei.tqs.aeolus.external_api.weather_bit_utils;

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

    /**
     * verify empty response
     * @return
     * true -> empty response
     * false -> response not empty
     */
    public boolean empty() {
        if (
                city_name == null ||
                        lon == null ||
                        lat == null ||
                        country_code == null ||
                        state_code == null
        )
            return true;

        return false;
    }
}
