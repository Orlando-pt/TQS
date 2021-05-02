package lei.tqs.aeolus.cache;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class CacheObject<V> {
    private long lastAccessed = System.currentTimeMillis();
    private int timesRequested;
    private V value;

    public CacheObject(V value) {
        this.value = value;
        this.timesRequested = 1;
    }

    public void accessed(long time) {
        this.lastAccessed = time;
        this.timesRequested++;
    }
}
