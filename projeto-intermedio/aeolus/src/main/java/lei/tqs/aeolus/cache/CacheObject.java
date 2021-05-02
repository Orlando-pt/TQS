package lei.tqs.aeolus.cache;

public class CacheObject<V> {
    private long lastAccessed = System.currentTimeMillis();
    private V value;

    public CacheObject(V value) {
        this.value = value;
    }

    public long getLastAccessed() { return this.lastAccessed; }
    public void setLastAccessed(long time) { this.lastAccessed = time; }
    public V getValue() { return this.value; }
    public void setValue(V value) { this.value = value; }
}
