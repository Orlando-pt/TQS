package lei.tqs.aeolus.external_api;

import lombok.*;

import java.util.Calendar;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class Measure {
    private float co;
    private float no2;
    private float o3;
    private float so2;
    private float pm10;
    private Calendar timestamp;
}
