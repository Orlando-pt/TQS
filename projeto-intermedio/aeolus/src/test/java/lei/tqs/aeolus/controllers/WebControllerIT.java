package lei.tqs.aeolus.controllers;

import lombok.extern.log4j.Log4j2;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.List;

@Log4j2
class WebControllerIT {

    /**
     * HtmlUnitDriver won't work because it doesn't load javascript natively
     * we need to call a method to enable it and wait until the browser loads the javascript content
     * @param driver
     */

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        var options = new FirefoxOptions();
        options.setHeadless(true);

        this.driver = new FirefoxDriver(options);
    }

    @Test
    void testCurrentAirQualityWorkFlow() {
        this.driver.get("https://tqs-mid-term-project.ew.r.appspot.com/");
        this.driver.findElement(By.id("input_city")).click();
        this.driver.findElement(By.id("input_city")).clear();
        this.driver.findElement(By.id("input_city")).sendKeys("Porto");
        this.driver.findElement(By.id("select_city")).click();
        this.driver.findElement(By.cssSelector(".col > form > input:nth-child(5)")).click();
        MatcherAssert.assertThat(this.driver.findElement(By.cssSelector("h1")).getText(), Matchers.is("Current air quality in Porto"));
        {
            List<WebElement> elements = this.driver.findElements(By.cssSelector("tbody th"));
            assert(elements.size() > 0);
        }
    }

    @Test
    void testHistoryAirQualityWorkFlow() {
        this.driver.get("https://tqs-mid-term-project.ew.r.appspot.com/");
        this.driver.findElement(By.id("input_city")).click();
        this.driver.findElement(By.id("input_city")).clear();
        this.driver.findElement(By.id("input_city")).sendKeys("Madrid");
        this.driver.findElement(By.id("input_country")).click();
        this.driver.findElement(By.id("input_country")).clear();
        this.driver.findElement(By.id("input_country")).sendKeys("Espanha");
        this.driver.findElement(By.id("select_city")).click();
        this.driver.findElement(By.id("days_before")).click();
        this.driver.findElement(By.id("days_before")).clear();
        this.driver.findElement(By.id("days_before")).sendKeys("2");
        this.driver.findElement(By.cssSelector("input:nth-child(7)")).click();
        MatcherAssert.assertThat(this.driver.findElement(By.cssSelector("h1")).getText(), CoreMatchers.is("History air quality in Madrid"));
        {
            List<WebElement> elements = this.driver.findElements(By.cssSelector("tr:nth-child(18) > th"));
            assert(elements.size() > 0);
        }
    }
}