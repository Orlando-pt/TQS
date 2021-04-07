package pop.ex2WebPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FlightsPage {

    private WebDriver driver;

    @FindBy(css = "h3")
    private WebElement heading;

    @FindBy(css = "tr:nth-child(2) .btn")
    private WebElement flight_choice;

    public FlightsPage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    public void choseSecondFlight() {
        this.flight_choice.click();
    }

    public boolean verifyHeading() {
        return this.heading.getText().equals("Flights from Paris to London:");
    }
}
