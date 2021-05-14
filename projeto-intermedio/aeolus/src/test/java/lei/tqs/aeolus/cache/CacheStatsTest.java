package lei.tqs.aeolus.cache;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class CacheStatsTest {

    private Cache<ImmutablePair, String> cache;
    private ImmutablePair<String, String> aveiro = new ImmutablePair<>("40.640506", "-8.653754");
    private ImmutablePair<String, String> lisboa = new ImmutablePair<>("38.736946", "-9.142685");

    private CacheStats stats;

    @BeforeEach
    void setUp() {
        stats = Mockito.mock(CacheStats.class);

        this.cache = new Cache<ImmutablePair, String>(2, 1, 10);

        this.cache.get(this.aveiro);
        this.cache.put(this.aveiro, "a very precise weather prevision");
        this.cache.get(this.lisboa);
        this.cache.put(this.lisboa, "another very good prevision of the weather");

        this.cache.setCacheStats(stats);
    }

    @AfterEach
    void tearDown() {
        this.cache = null;
    }

    @Test
    void percentageOfSuccessfulRequestsAnsweredTest() {
        Mockito.when(stats.getRequests()).thenReturn(23l);
        Mockito.when(stats.getRequestsCachedAndAnswered()).thenReturn(8l);

        Assertions.assertThat(
                this.cache.percentageOfSuccessfulRequestsAnswered()
        ).isEqualTo(34.78);

        Mockito.verify(stats, Mockito.times(1)).getRequests();
        Mockito.verify(stats, Mockito.times(1)).getRequestsCachedAndAnswered();
    }

    @Test
    void percentageOfSucessfulRequestsAnswered_withRealCacheStatsObjectTest() {
        Cache<ImmutablePair, String> realCache = new Cache<>(2, 1, 10);

        realCache.get(this.aveiro);
        realCache.put(this.aveiro, "a very precise weather prevision");
        realCache.get(this.lisboa);
        realCache.put(this.lisboa, "another very good prevision of the weather");

        simulate9RequestsToAveiro(realCache);

        // check if in total there were 11 requests
        Assertions.assertThat(
                realCache.numberOfRequests()
        ).isEqualTo(11L);

        // 9 requests were answered with cache
        Assertions.assertThat(
                realCache.requestsAnswered()
        ).isEqualTo(9L);

        // and the percentage of requests answered is 81.82%
        Assertions.assertThat(
            realCache.percentageOfSuccessfulRequestsAnswered()
        ).isEqualTo(81.82);
    }

    @Test
    void mostRequestedTest() {
        // simulate requests to the cache
        simulateRequests();

        // call cache method
        ImmutablePair<String, String> mostRequested = this.cache.mostRequested();

        // verify if Aveiro is the most requested
        Assertions.assertThat(
                mostRequested
        ).isEqualTo(this.aveiro);

        // verify if Aveiro was requested exactly 11 times
        Assertions.assertThat(
                this.cache.requestsToKey(this.aveiro)
        ).isEqualTo(11l);

        Mockito.verify(stats, Mockito.times(15)).incAnsweredRequests();
    }

    @Test
    void whenTryToCheckTheRequestOfNoCachedValue_ThenReturn0() {
        Assertions.assertThat(
                this.cache.requestsToKey(ImmutablePair.of("0", "0"))
        ).isZero();
    }

    private void simulateRequests() {
        // simulate 10 requests to Aveiro
        var i = 0;
        for (; i < 10; i++)
            this.cache.get(this.aveiro);

        // simulate 5 requests to Lisboa
        for (i = 0; i < 5; i++)
            this.cache.get(this.lisboa);
    }

    private void simulate9RequestsToAveiro(Cache cache) {
        var i = 0;
        for (; i < 9; i++)
            cache.get(this.aveiro);
    }
}
