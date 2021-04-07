package pop.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pop.ex2WebPages.CheckPurchasePage;
import pop.ex2WebPages.FlightsPage;
import pop.ex2WebPages.HomePageBlaze;
import pop.ex2WebPages.SubFormPage;

import java.util.concurrent.TimeUnit;

public class BlazeDemoTest {

    private WebDriver driver;

    @BeforeEach
    public void setup() {
        this.driver = new FirefoxDriver();
        this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterEach
    public void close() {
        this.driver.close();
    }

    @Test
    public void parisLondonFlightTest() {
        // create homePage
        HomePageBlaze homePage = new HomePageBlaze(this.driver);
        Assertions.assertTrue(homePage.isOnPage());
        homePage.choseDepartureCity();
        homePage.choseDestinationCity();
        homePage.findFlightsButton();

        // create flights available page
        FlightsPage flightsPage = new FlightsPage(this.driver);
        Assertions.assertTrue(flightsPage.verifyHeading());
        flightsPage.choseSecondFlight();

        // create form submit page
        SubFormPage subFormPage = new SubFormPage(this.driver);
        Assertions.assertTrue(subFormPage.isOnPage());
        subFormPage.setInputName("António José");
        subFormPage.setZipCode("3333");
        subFormPage.setCreditCardNumber("1231313131312312312312");
        subFormPage.submitForm();

        // create final page
        CheckPurchasePage checkPurchasePage = new CheckPurchasePage(this.driver);
        Assertions.assertTrue(checkPurchasePage.verifyTitle());
    }
}
