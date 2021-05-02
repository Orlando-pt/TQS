package lei.tqs.aeolus.cache;

import java.util.Optional;
import java.util.Set;

public interface CacheInterface<K, V> {
    void put(K key, V value);
    Optional<V> get(K key);
    void remove(K key);
    int size();
    void cleanup();
    Set<K> getKeys();

    boolean isFull();
    int maxSize();
    boolean containsKey(K key);

    // stats
    long numberOfRequests();
    long requestsToKey(K key);
    long requestsAnswered();
    K mostRequested();
    double percentageOfSuccessfulRequestsAnswered();
}
