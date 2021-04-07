package pop.ex2WebPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePageBlaze {

    private static String PAGE_URL = "https://blazedemo.com/";

    private WebDriver driver;

    @FindBy(name = "fromPort")
    private WebElement dep_button;

    @FindBy(css = ".form-inline:nth-child(1) > option:nth-child(1)")
    private WebElement dep_option;

    @FindBy(name = "toPort")
    private WebElement dest_button;

    @FindBy(css = ".btn-primary")
    private WebElement find_Flights_button;

    public HomePageBlaze(WebDriver driver) {
        this.driver = driver;

        this.driver.get(PAGE_URL);

        PageFactory.initElements(driver, this);
    }

    public void choseDepartureCity() {
        this.dep_button.click();
        this.dep_option.click();
    }

    public void choseDestinationCity() {
        this.dest_button.click();
        this.dest_button.findElement(By.xpath("//option[. = 'London']")).click();
    }

    public void findFlightsButton() {
        this.find_Flights_button.click();
    }

    public boolean isOnPage() {
        return this.driver.getTitle().equals("BlazeDemo");
    }
}
