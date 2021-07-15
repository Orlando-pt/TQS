package lei.tqs.aeolus.cache;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CacheStats {
    private long requests;
    private long requestsCachedAndAnswered;

    public CacheStats() {
        this.requests = 0;
        this.requestsCachedAndAnswered = 0;
    }

    public void incRequests() {
        this.requests++;
    }

    public void incAnsweredRequests() {
        this.requests++;
        this.requestsCachedAndAnswered++;
    }
}
