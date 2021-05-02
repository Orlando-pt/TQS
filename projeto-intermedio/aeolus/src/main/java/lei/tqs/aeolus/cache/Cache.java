package lei.tqs.aeolus.cache;

import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.map.LRUMap;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

public class Cache<K, V> implements CacheInterface<K, V> {
    private long timeToLive;
    /**
     * LRUMap
     * A Map implementation with a fixed maximum size which removes
     * the least recently used entry if an entry is added when full.
     */
    private LRUMap cacheMap;

    protected class CacheObject {
        public long lastAccessed = System.currentTimeMillis();
        public V value;

        protected CacheObject(V value) {
            this.value = value;
        }
    }

    public Cache(long timeToLive, final long timerInterval, int maxItems) {
        this.timeToLive = timeToLive * 1000;

        cacheMap = new LRUMap(maxItems);

        if (timeToLive > 0 && timerInterval > 0) {
            Thread t = new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            while (true) {
                                try {
                                    Thread.sleep(timerInterval * 1000);
                                } catch (InterruptedException ex) {

                                }
                                cleanup();
                            }
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
            this.cacheMap.put(key, new CacheObject(value));
        }
    }

    @Override
    public Optional<V> get(K key) {
        synchronized (this.cacheMap) {
            CacheObject object = (CacheObject) this.cacheMap.get(key);

            if (object == null) {
                return Optional.empty();
            } else {
                object.lastAccessed = System.currentTimeMillis();
                return Optional.of(object.value);
            }
        }
    }

    @Override
    public void remove(K key) {
        synchronized (this.cacheMap) {
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
    public void cleanup() {
        long now = System.currentTimeMillis();
        ArrayList<K> deletekeys = null;

        synchronized (this.cacheMap) {
            MapIterator itr = this.cacheMap.mapIterator();

            deletekeys = new ArrayList<K>((this.cacheMap.size()/2) + 1);
            K key = null;
            CacheObject cacheObject = null;

            while(itr.hasNext()) {
                key = (K) itr.next();
                cacheObject = (CacheObject) itr.getValue();

                if (cacheObject != null && (now > (this.timeToLive + cacheObject.lastAccessed)))
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
}
