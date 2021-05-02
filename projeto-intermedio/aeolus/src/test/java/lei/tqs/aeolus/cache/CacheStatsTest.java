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
    // TODO alterar nome dos testes

    private Cache<ImmutablePair, String> cache;
    private ImmutablePair<String, String> aveiro = new ImmutablePair<>("40.640506", "-8.653754");
    private ImmutablePair<String, String> lisboa = new ImmutablePair<>("38.736946", "-9.142685");

    private CacheStats stats;

    @BeforeEach
    void setUp() {
        stats = Mockito.mock(CacheStats.class);

        this.cache = new Cache<ImmutablePair, String>(2, 1, 10);

        this.cache.put(this.aveiro, "a very precise weather prevision");
        this.cache.put(this.lisboa, "another very good prevision of the weather");

        this.cache.setCacheStats(stats);
    }

    @AfterEach
    void tearDown() {
        this.cache = null;
    }

    @Test
    void percentageOfSuccessfulRequestsAnswered() {
        Mockito.when(stats.getRequests()).thenReturn(23l);
        Mockito.when(stats.getRequestsCachedAndAnswered()).thenReturn(8l);

        Assertions.assertThat(
                this.cache.percentageOfSuccessfulRequestsAnswered()
        ).isEqualTo(34.78);
    }

    @Test
    void mostRequested() {
        // simulate requests to the cache
        simulateRequests();

        // call cache method
        ImmutablePair<String, String> mostRequested = this.cache.mostRequested();

        // verify if Aveiro is the most requested
        Assertions.assertThat(
                mostRequested
        ).isEqualTo(this.aveiro);

        // verify if Aveiro was requested exactly 10 times
        Assertions.assertThat(
                this.cache.requestsToKey(this.aveiro)
        ).isEqualTo(11l);
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
}
