package lei.tqs.aeolus.ExternalAPI;

import lombok.*;

@Setter @Getter @NoArgsConstructor @ToString
@AllArgsConstructor
public class APIResponse {

    private String latitude;
    private String longitude;

    private float co;
    private float no;
    private float no2;
    private float o3;
    private float so2;
    private float pm10;
    private float nh3;
}
