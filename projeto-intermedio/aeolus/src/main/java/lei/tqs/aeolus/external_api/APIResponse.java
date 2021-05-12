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

    /**
     * verify empty response
     * if latitude and longitude aren't filled
     * this counts as a empty response
     * @return
     */
    public boolean empty() {
        if (this.latitude == null || this.longitude == null)
            return true;

        return false;
    }
}
