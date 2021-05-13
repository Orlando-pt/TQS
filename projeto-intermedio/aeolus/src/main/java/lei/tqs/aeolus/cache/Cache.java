package lei.tqs.aeolus.cache;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.map.LRUMap;

import java.util.*;

@Log4j2
public class Cache<K, V> implements CacheInterface<K, V> {
    private final long timeToLive;
    private CacheStats stats;

    // TODO colocar aqui o logging

    /**
     * LRUMap
     * A Map implementation with a fixed maximum size which removes
     * the least recently used entry if an entry is added when full.
     */
    private final LRUMap<K, CacheObject<V>> cacheMap;

    public Cache(long timeToLive, final long timerInterval, int maxItems) {
        this.timeToLive = timeToLive * 1000;

        this.cacheMap = new LRUMap<>(maxItems);
        this.stats = new CacheStats();

        if (timeToLive > 0 && timerInterval > 0) {
            var t = new Thread(
                    () -> {
                        var keepGoing = true;
                        while (keepGoing) {
                            try {
                                Thread.sleep(timerInterval * 1000);
                            } catch (InterruptedException ex) {
                                keepGoing = false;
                                Thread.currentThread().interrupt();
                            }
                            cleanup();
                        }
                    }
            );
            t.setDaemon(true);
            t.start();
        }
    }

    @Override
    public void put(K key, V value) {
        synchronized (this.cacheMap) {
            log.info("Storing on cache: " + value);
            this.stats.incRequests();
            this.cacheMap.put(key, new CacheObject<>(value));
        }
    }

    @Override
    public Optional<V> get(K key) {
        synchronized (this.cacheMap) {
            log.info("Retrieving from cache the location (" + key + ")");
            CacheObject<V> object = this.cacheMap.get(key);

            if (object == null) {
                this.stats.incRequests();

                return Optional.empty();
            } else {
                this.stats.incAnsweredRequests();

                object.accessed(System.currentTimeMillis());
                return Optional.of(object.getValue());
            }
        }
    }

    @Override
    public void remove(K key) {
        synchronized (this.cacheMap) {
            log.info("Removing from cache the location: " + key);
            this.cacheMap.remove(key);
        }
    }

    @Override
    public int size() {
        synchronized (this.cacheMap) {
            return this.cacheMap.size();
        }
    }

    @Override
    public Set<K> getKeys() {
        synchronized (this.cacheMap) {
            return this.cacheMap.keySet();
        }
    }

    @Override
    public boolean isFull() {
        synchronized (this.cacheMap) {
            return this.cacheMap.isFull();
        }
    }

    @Override
    public int maxSize() {
        synchronized (this.cacheMap) {
            return this.cacheMap.maxSize();
        }
    }

    @Override
    public boolean containsKey(K key) {
        synchronized (this.cacheMap) {
            return this.cacheMap.containsKey(key);
        }
    }

    @Override
    public long numberOfRequests() {
        return this.stats.getRequests();
    }

    @Override
    public long requestsToKey(K key) {
        synchronized (this.cacheMap) {
            CacheObject<V> object = this.cacheMap.get(key);

            if (object == null) {
                return 0;
            } else {
                return object.getTimesRequested();
            }
        }
    }

    @Override
    public long requestsAnswered() {
        return this.stats.getRequestsCachedAndAnswered();
    }

    @Override
    public K mostRequested() {
        synchronized (this.cacheMap) {
            return Collections.max(this.cacheMap.entrySet(), (e1, e2) -> e1.getValue().getTimesRequested() - e2.getValue().getTimesRequested()).getKey();
        }
    }

    @Override
    public double percentageOfSuccessfulRequestsAnswered() {
        return Math.round(((float) this.stats.getRequestsCachedAndAnswered() / this.stats.getRequests()) * 10000d) / 100d;
    }

    @Override
    public void cleanup() {
        long now = System.currentTimeMillis();
        ArrayList<K> deletekeys = null;

        synchronized (this.cacheMap) {
            log.info("Performing cleaning of cache");
            MapIterator<K, CacheObject<V>> itr = this.cacheMap.mapIterator();

            deletekeys = new ArrayList<>((this.cacheMap.size()/2) + 1);
            K key = null;
            CacheObject<V> cacheObject = null;

            while(itr.hasNext()) {
                key = itr.next();
                cacheObject = itr.getValue();

                if (cacheObject != null && (now > (this.timeToLive + cacheObject.getLastAccessed())))
                    deletekeys.add(key);
            }
        }

        // remove the stored values that expired
        for (K key : deletekeys) {
            synchronized (this.cacheMap) {
                this.cacheMap.remove(key);
            }

            Thread.yield();
        }
    }

    public void setCacheStats(CacheStats stats) { this.stats = stats; }
}
