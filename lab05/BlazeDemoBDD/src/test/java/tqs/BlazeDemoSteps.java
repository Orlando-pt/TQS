package tqs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BlazeDemoSteps {
    private WebDriver webDriver;
    private WebElement lastWebElement;

    @When("a customer goes to the url {string}")
    public void getUrl(final String url) {
        WebDriverManager.firefoxdriver().setup();
        this.webDriver = new FirefoxDriver();
        this.webDriver.get(url);
    }

    @Then("the customer clicks the element with name {string}")
    public void clickElementWithName(final String name) {
        this.lastWebElement = this.webDriver.findElement(By.name(name));
        this.lastWebElement.click();
    }


    @And("the customer clicks the css element with the name {string}")
    public void clickElementUsingCssId(final String css) {
        this.lastWebElement = this.webDriver.findElement(By.cssSelector(css));
        this.lastWebElement.click();
    }

    @And("the customer clicks the option {string}")
    public void theCustomerClicksTheOptionOptionLondon(final String xpath) {
        this.lastWebElement.findElement(By.xpath(xpath)).click();
    }

    @Then("the customer should see the {string} element with the text {string}")
    public void verifyIfElementHasTheRightText(final String element, final String text) {
        MatcherAssert.assertThat(
                this.webDriver.findElement(By.cssSelector(element)).getText(),
                CoreMatchers.is(text)
        );
    }

    @And("the customer clicks the element with id {string}")
    public void clickElementWithId(final String id) {
        this.lastWebElement = this.webDriver.findElement(By.id(id));
        this.lastWebElement.click();
    }

    @And("the customer sends the keys {string}")
    public void sendKeys(final String keys) {
        this.lastWebElement.sendKeys(keys);
    }

    @Then("the customer verifies that the title of the page is {string}")
    public void verifyTheTitlePage(final String title) {
        MatcherAssert.assertThat(this.webDriver.getTitle(), CoreMatchers.is(title));
    }

    @Then("the customer can quit from the website")
    public void theCustomerCanQuitFromTheWebsite() {
        this.webDriver.quit();
    }
}
