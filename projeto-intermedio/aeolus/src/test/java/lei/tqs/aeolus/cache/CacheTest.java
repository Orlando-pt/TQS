package lei.tqs.aeolus.cache;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

class CacheTest {

    private Cache<ImmutablePair, String> cache;
    private ImmutablePair<String, String> aveiro = new ImmutablePair<>("40.640506", "-8.653754");
    private ImmutablePair<String, String> lisboa = new ImmutablePair<>("38.736946", "-9.142685");

    // clock object to simulate delays
    private SystemClock systemClock = new SystemClock();

    // verify maxed out cache
    private Cache<ImmutablePair, String> maxedOutCache;

    @BeforeEach
    void setUp() {
        // the cache will be checking if there are stored values with expired time to live after each second
        this.cache = new Cache<ImmutablePair, String>(2, 1, 10);

        this.cache.put(this.aveiro, "a very precise weather prevision");
        this.cache.put(this.lisboa, "another very good prevision of the weather");


        // example with a full cache
        this.maxedOutCache = new Cache<ImmutablePair, String>(10, 5, 2);

        this.maxedOutCache.put(this.aveiro, "Very sunny");
        this.maxedOutCache.put(this.lisboa, "Raining coca-cola");
    }

    @AfterEach
    void tearDown() {
        this.cache = null;
        this.maxedOutCache = null;
    }

    @Test
    void whenPutValueOnCache_ifTimeNotPassed_ThenTheValueShouldBeRetrieved() throws InterruptedException {

        /**
         * sleep for 1 second
         * less than the time to live
         */
        // systemClock.sleep(1);
        TimeUnit.SECONDS.sleep(1);

        Optional<String> value = this.cache.get(this.aveiro);

        Assertions.assertThat(value.isPresent())
                .isTrue()
                .withFailMessage(() -> "When a value is placed on cache and time to live" +
                        " hasn't expired yet, on get() the value should be returned");

        Assertions.assertThat(value.get())
                .isEqualTo("a very precise weather prevision")
                .withFailMessage(() -> "When a value is placed on cache and time to live" +
                        " hasn't expired yet, on get() the value should be returned");
    }

    @Test
    void whenPutValueOnCache_ifTimeToLivePassed_ThenNoValueShouldBeRetrieved() throws InterruptedException{

        /**
         * sleep for 3 second
         * more than the time to live
         *
         * this scenario is also applied when a non stored value is requested on the cache
         * the cache should return a empty response
         */
        // systemClock.sleep(3);
        TimeUnit.SECONDS.sleep(3);

        Optional<String> value = this.cache.get(this.aveiro);

        Assertions.assertThat(value.isEmpty())
                .isTrue()
                .withFailMessage(() -> "When a value is placed on cache and time to live" +
                        " has expired, on get() no value should be returned");
    }

    @Test
    void whenTheCacheIsFull_TheLeastRecentlyValueShouldBeRemoved() {
        ImmutablePair<String, String> nullIsland = new ImmutablePair<String, String>("0", "0");

        // update the last accessed attribute for Aveiro cache stored value
        this.maxedOutCache.get(this.aveiro);

        // store a more recent request
        this.maxedOutCache.put(
                nullIsland, "Shark making a tornado at the atlantic"
        );

        // verify that, Lisboa is no longer cached
        Optional<String> noValue = this.maxedOutCache.get(this.lisboa);
        Assertions.assertThat(
            noValue.isEmpty()
        ).isTrue().withFailMessage(() ->
                "Lisboa is the least recently used value on cache. It should have been removed.");

        //verify that, the size of the cache is 2
        Assertions.assertThat(
                maxedOutCache.size()
        ).isEqualTo(2).withFailMessage(() ->
                "The size of the cache should be 2");

        // verify that Aveiro and nullIsland are still cached
        Assertions.assertThat(
                this.maxedOutCache.getKeys()
        ).contains(this.aveiro, nullIsland).withFailMessage(() ->
                "The last 2 recently used cached values should be returned");
    }

    @Test
    void remove() {
        this.cache.remove(this.lisboa);

        Optional<String> noValue = this.cache.get(this.lisboa);

        Assertions.assertThat(noValue.isEmpty())
                .isTrue()
                .withFailMessage(() ->
                        "When a chached value is removed, on get()" +
                                "no value should be returned");
    }

    @Test
    void size() {
        Assertions.assertThat(
                this.cache.size()
        ).isEqualTo(2)
                .withFailMessage(() ->
                        "Two values were stored on cache. So, the size of the cache should return the value 2");
    }

    @Test
    void isFull() {
        Assertions.assertThat(
                this.maxedOutCache.isFull()
        ).isTrue().withFailMessage(() ->
                "The cache has a max size of 2 stored values. So, it should return true.");
    }

    @Test
    void maxSize() {
        Assertions.assertThat(
                this.cache.maxSize()
        ).isEqualTo(10).withFailMessage(() ->
                "The cache has a max size of 10 stored values. So, it should return 10.");
    }

    @Test
    void containsKey() {
        Assertions.assertThat(
                this.cache.containsKey(this.lisboa)
        ).isTrue().withFailMessage(() -> "Lisboa is stored on cache. " +
                "As consequence, the cache should return true");

        Assertions.assertThat(
                this.cache.containsKey(new ImmutablePair<String, String>("0", "0"))
        ).isFalse().withFailMessage(() -> "NullIsland isn't stored on cache. " +
                "As consequence, the cache should return false");
    }

}