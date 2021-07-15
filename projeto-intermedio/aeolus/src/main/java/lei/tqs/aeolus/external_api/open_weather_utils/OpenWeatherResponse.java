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

    /**
     * verify empty response
     * if latitude and longitude aren't filled
     * this counts as a empty response
     * @return
     */
    public boolean empty() {
        if (coord == null)
            return true;

        if (this.coord.empty())
            return true;

        return false;
    }
}
