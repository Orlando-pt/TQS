package pop.ex2WebPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class CheckPurchasePage {

    private WebDriver driver;

    public CheckPurchasePage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    public boolean verifyTitle() {
        return this.driver.getTitle().equals("BlazeDemo Confirmation");
    }
}
