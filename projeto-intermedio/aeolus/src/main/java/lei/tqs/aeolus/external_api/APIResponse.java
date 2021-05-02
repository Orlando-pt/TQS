package lei.tqs.aeolus.external_api;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter @Getter @NoArgsConstructor @ToString
@AllArgsConstructor
public class APIResponse {

    private String latitude;
    private String longitude;

    private List<Measure> measureList;

    public void addMeasure(Measure measure) {
        if (this.measureList == null)
            this.measureList = new ArrayList<>();

        this.measureList.add(measure);
    }
}
