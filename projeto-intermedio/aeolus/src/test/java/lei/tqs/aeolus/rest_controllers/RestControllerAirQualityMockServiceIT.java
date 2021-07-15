package lei.tqs.aeolus.rest_controllers;

import lei.tqs.aeolus.external_api.APIResponse;
import lei.tqs.aeolus.external_api.Measure;
import lei.tqs.aeolus.services.AqServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@WebMvcTest(RestControllerAirQuality.class)
class RestControllerAirQualityMockServiceIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AqServiceInterface service;

    private APIResponse responseCurrent;
    private APIResponse responseHistory;
    private Calendar birth;

    @BeforeEach
    void setUp() {
        var measure1 = new Measure(1.0f,
                1.0f,
                1.0f,
                1.0f,
                1.0f,
                Calendar.getInstance());
        var measure2 = new Measure(2.0f,
                2.0f,
                2.0f,
                2.0f,
                2.0f,
                Calendar.getInstance());
        List<Measure> measure = Arrays.asList(measure1);

        this.responseCurrent = new APIResponse("1", "2", Arrays.asList(measure1));
        this.responseHistory = new APIResponse("1", "2", Arrays.asList(measure1, measure2));

        this.birth = Calendar.getInstance();
        this.birth.clear();
        this.birth.set(2000, 6, 4, 17, 0);
    }

    @Test
    void getCurrentAirQualityTest_verifyResponse() throws Exception{
        Mockito.when(this.service.getCurrentAQ(
                this.responseCurrent.getLatitude(), this.responseCurrent.getLongitude()
        )).thenReturn(this.responseCurrent);

        this.mvc.perform(
                get("/api/aq/currentaq?lat=" +
                        this.responseCurrent.getLatitude() + "&lng=" +
                        this.responseCurrent.getLongitude())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                jsonPath("$.latitude", is("1"))
        ).andExpect(
                jsonPath("$.measureList[0].co", is(1.0))
        );

        Mockito.verify(this.service, Mockito.times(1)).getCurrentAQ(
                this.responseCurrent.getLatitude(), this.responseCurrent.getLongitude()
        );
    }

    @Test
    void getHistoryAirQualityTest_verifyMultipleMeasures() throws Exception {
        Mockito.when(this.service.getHistoryAQPreviousDays(
                this.responseCurrent.getLatitude(), this.responseCurrent.getLongitude(), 2
        )).thenReturn(this.responseHistory);

        this.mvc.perform(
                get("/api/aq/history?lat=" +
                        this.responseCurrent.getLatitude() + "&lng=" +
                        this.responseCurrent.getLongitude() + "&days=" + 2)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                jsonPath("$.latitude", is("1"))
        ).andExpect(
                jsonPath("$.measureList[0].co", is(1.0))
        ).andExpect(
                jsonPath("$.measureList[1].co", is(2.0))
        );

        Mockito.verify(this.service, Mockito.times(1)).getHistoryAQPreviousDays(
                this.responseCurrent.getLatitude(), this.responseCurrent.getLongitude(), 2
        );
    }

    @Test
    void verifyParseOfCalendarOnUrl() throws Exception {
        Mockito.when(this.service.getHistoryAQByDayAndHourUntilPresent(
                this.responseCurrent.getLatitude(), this.responseCurrent.getLongitude(), this.birth
        )).thenReturn(this.responseHistory);

        this.mvc.perform(
                get("/api/aq/fromday?lat=" +
                        this.responseCurrent.getLatitude() + "&lng=" +
                        this.responseCurrent.getLongitude() + "&year=" +
                        "2000&month=7&day=4&hour=17")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                jsonPath("$.latitude", is("1"))
        ).andExpect(
                jsonPath("$.measureList[0].co", is(1.0))
        ).andExpect(
                jsonPath("$.measureList[1].co", is(2.0))
        );

        Mockito.verify(this.service, Mockito.times(1)).getHistoryAQByDayAndHourUntilPresent(
                this.responseCurrent.getLatitude(), this.responseCurrent.getLongitude(), this.birth
        );
    }

    @Test
    void getHistoryAirQualityBetweenDaysTestVerifyUrlParsing() throws Exception{
        var nextDay = (Calendar) this.birth.clone();
        nextDay.add(Calendar.DAY_OF_MONTH, 1);
        Mockito.when(this.service.getHistoryAQBetweenDays(
                this.responseCurrent.getLatitude(), this.responseCurrent.getLongitude(), this.birth, nextDay
        )).thenReturn(this.responseHistory);

        this.mvc.perform(
                get("/api/aq/betweendays?lat=" +
                        this.responseCurrent.getLatitude() + "&lng=" +
                        this.responseCurrent.getLongitude() + "&initialYear=" +
                        "2000&initialMonth=7&initialDay=4&initialHour=17&endYear=" +
                        "2000&endMonth=7&endDay=5&endHour=17")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                jsonPath("$.latitude", is("1"))
        ).andExpect(
                jsonPath("$.measureList[0].co", is(1.0))
        ).andExpect(
                jsonPath("$.measureList[1].co", is(2.0))
        );

        Mockito.verify(this.service, Mockito.times(1)).getHistoryAQBetweenDays(
                this.responseCurrent.getLatitude(), this.responseCurrent.getLongitude(), this.birth, nextDay
        );
    }
}