package lei.tqs.aeolus.rest_controllers;

import lei.tqs.aeolus.services.AqServiceInterface;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.HashSet;

@WebMvcTest(RestControllerCache.class)
class RestControllerCacheWithMockServiceIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AqServiceInterface aqService;

    @Test
    void whenCacheHas20Locations_thenSizeShouldReturn20() throws Exception{
        Mockito.when(this.aqService.cacheSize()).thenReturn(
                20
        );

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/cache/size")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                        MockMvcResultMatchers.jsonPath("$", CoreMatchers.is(20))
        );

        Mockito.verify(this.aqService, VerificationModeFactory.times(1)).cacheSize();
    }

    @Test
    void getCachedLocationsTest() throws Exception {
        var response = new HashSet<>(Arrays.asList(ImmutablePair.of("40", "-8"), ImmutablePair.of("40", "-5")));
        Mockito.when(this.aqService.getCachedLocations()).thenReturn(
                response
        );

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/cache/cachedlocations")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));

        Mockito.verify(this.aqService, Mockito.times(1)).getCachedLocations();
    }

    @Test
    void whenLocationIsStoredOnCache_thenTrueIfAskedIfContains() throws Exception{
        var location = ImmutablePair.of("1" , "2");

        Mockito.when(this.aqService.cacheContainsLocation(location)).thenReturn(Boolean.TRUE);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get(
                        "/api/cache/cachecontains?lat=" + location.getLeft() +
                        "&lng=" + location.getRight())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$", CoreMatchers.is(true))
        );

        Mockito.verify(this.aqService, Mockito.times(1)).cacheContainsLocation(location);
    }

}