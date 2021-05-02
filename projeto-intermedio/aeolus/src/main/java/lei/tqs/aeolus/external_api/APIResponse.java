package lei.tqs.aeolus.external_api;

import lombok.*;

import java.util.List;

@Setter @Getter @NoArgsConstructor @ToString
@AllArgsConstructor
public class APIResponse {

    private String latitude;
    private String longitude;

    private List<Measure> measureList;

    public void addMeasure(Measure measure) {
        this.measureList.add(measure);
    }
}
