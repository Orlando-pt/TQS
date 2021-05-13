package lei.tqs.aeolus.rest_controllers;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lei.tqs.aeolus.services.AqServiceInterface;
import org.hamcrest.CoreMatchers;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(RestControllerCache.class)
class RestControllerCacheWithMockServiceCucumberITTestSteps {

    private ResultActions currentRequestPerformed;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AqServiceInterface aqService;

    @Given("that the cache has currently {size} locations stored")
    public void mockCacheSize(int size) {
        Mockito.when(this.aqService.cacheSize()).thenReturn(
                20
        );
    }

    @When("a request of {string} is received on REST api")
    public void performGetRequest(String url) throws Exception {
        this.currentRequestPerformed = this.mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
        );
    }

    @Then("the REST api should respond with the value {int}")
    public void verifySoloIntResponse(int value) throws Exception{
        this.currentRequestPerformed.andExpect(
                MockMvcResultMatchers.jsonPath("$", CoreMatchers.is(value))
        );
    }

    @And("the method cacheSize should be called {int} time")
    public void timesCachedSizeCalled(int calls) {
        Mockito.verify(this.aqService, VerificationModeFactory.times(1)).cacheSize();
    }

}