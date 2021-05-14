package lei.tqs.aeolus.controllers;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import lombok.extern.log4j.Log4j2;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

@Log4j2
@ExtendWith(SeleniumJupiter.class)
class WebControllerIT {

    /**
     * HtmlUnitDriver won't work because it doesn't load javascript natively
     * we need to call a method to enable it and wait until the browser loads the javascript content
     * @param driver
     */

    @Test
    void testCurrentAirQualityWorkFlow(FirefoxDriver driver) {
        driver.get("https://tqs-mid-term-project.ew.r.appspot.com/");
        driver.findElement(By.id("input_city")).click();
        driver.findElement(By.id("input_city")).clear();
        driver.findElement(By.id("input_city")).sendKeys("Porto");
        driver.findElement(By.id("select_city")).click();
        driver.findElement(By.cssSelector(".col > form > input:nth-child(5)")).click();
        MatcherAssert.assertThat(driver.findElement(By.cssSelector("h1")).getText(), Matchers.is("Current air quality in Porto"));
        {
            List<WebElement> elements = driver.findElements(By.cssSelector("tbody th"));
            assert(elements.size() > 0);
        }
    }

    @Test
    void testHistoryAirQualityWorkFlow(FirefoxDriver driver) {
        driver.get("https://tqs-mid-term-project.ew.r.appspot.com/");
        driver.findElement(By.id("input_city")).click();
        driver.findElement(By.id("input_city")).clear();
        driver.findElement(By.id("input_city")).sendKeys("Madrid");
        driver.findElement(By.id("input_country")).click();
        driver.findElement(By.id("input_country")).clear();
        driver.findElement(By.id("input_country")).sendKeys("Espanha");
        driver.findElement(By.id("select_city")).click();
        driver.findElement(By.id("days_before")).click();
        driver.findElement(By.id("days_before")).clear();
        driver.findElement(By.id("days_before")).sendKeys("2");
        driver.findElement(By.cssSelector("input:nth-child(7)")).click();
        MatcherAssert.assertThat(driver.findElement(By.cssSelector("h1")).getText(), CoreMatchers.is("History air quality in Madrid"));
        {
            List<WebElement> elements = driver.findElements(By.cssSelector("tr:nth-child(18) > th"));
            assert(elements.size() > 0);
        }
    }
}