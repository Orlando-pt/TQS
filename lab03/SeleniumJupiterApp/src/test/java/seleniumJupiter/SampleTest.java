package seleniumJupiter;

import io.github.bonigarcia.seljup.BrowserType;
import io.github.bonigarcia.seljup.DockerBrowser;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SeleniumJupiter.class)
class SampleTest {

    // 3a)
    @Test
    void testWithOneFirefox(FirefoxDriver driver) {
        driver.get("https://bonigarcia.github.io/selenium-jupiter/");
        MatcherAssert.assertThat(driver.getTitle(),
                CoreMatchers.containsString("JUnit 5 extension for Selenium"));
    }

    // 3b)
    @Test
    void testNoHeaderBrowser(HtmlUnitDriver driver) {
        driver.get("https://bonigarcia.github.io/selenium-jupiter/");
        MatcherAssert.assertThat(driver.getTitle(),
                CoreMatchers.containsString("JUnit 5 extension for Selenium"));
    }

    // 3c)
    @Test
    void testExercise2WithInjection(FirefoxDriver driver) {
        driver.get("https://blazedemo.com/");
        driver.manage().window().setSize(new Dimension(550, 692));
        driver.findElement(By.name("fromPort")).click();
        driver.findElement(By.cssSelector(".form-inline:nth-child(1) > option:nth-child(1)")).click();
        driver.findElement(By.name("toPort")).click();
        {
            WebElement dropdown = driver.findElement(By.name("toPort"));
            dropdown.findElement(By.xpath("//option[. = 'London']")).click();
        }
        driver.findElement(By.cssSelector(".form-inline:nth-child(4) > option:nth-child(3)")).click();
        driver.findElement(By.cssSelector(".btn-primary")).click();
        assertEquals("Flights from Paris to London:", driver.findElement(By.cssSelector("h3")).getText());
        driver.findElement(By.cssSelector("tr:nth-child(2) .btn")).click();
        driver.findElement(By.id("inputName")).click();
        driver.findElement(By.id("inputName")).sendKeys("António José");
        driver.findElement(By.id("zipCode")).click();
        driver.findElement(By.id("zipCode")).sendKeys("3333");
        driver.findElement(By.id("cardType")).click();
        driver.findElement(By.id("cardType")).click();
        {
            WebElement dropdown = driver.findElement(By.id("cardType"));
            dropdown.findElement(By.xpath("//option[. = 'American Express']")).click();
        }
        driver.findElement(By.cssSelector("option:nth-child(2)")).click();
        driver.findElement(By.id("creditCardNumber")).click();
        driver.findElement(By.id("creditCardNumber")).sendKeys("1231313131312312312312");
        driver.findElement(By.cssSelector(".btn-primary")).click();
        assertEquals("BlazeDemo Confirmation", driver.getTitle());
    }

    // 3d)
    /**
     * Failed test with opera browser
     * (not installed)
     * @param driver1
     * @param driver2
     */
    @Disabled
    @Test
    void testWithOneFirefoxAndOneOpera(FirefoxDriver driver1,
                             OperaDriver driver2) {
        driver1.get("http://www.seleniumhq.org/");
        driver2.get("http://junit.org/junit5/");
        MatcherAssert.assertThat(driver1.getTitle(), CoreMatchers.startsWith("Selenium"));
        MatcherAssert.assertThat(driver2.getTitle(), CoreMatchers.equalTo("JUnit 5"));
    }

    /**
     * Running with opera browser with docker
     * @param driver1
     * @param driver2
     */
    @Test
    void testWithOneFirefoxAndOneOperaDocker(FirefoxDriver driver1,
                                             @DockerBrowser(type = BrowserType.OPERA) RemoteWebDriver driver2) {
        driver1.get("http://www.seleniumhq.org/");
        driver2.get("http://junit.org/junit5/");
        MatcherAssert.assertThat(driver1.getTitle(), CoreMatchers.startsWith("Selenium"));
        MatcherAssert.assertThat(driver2.getTitle(), CoreMatchers.equalTo("JUnit 5"));
    }

}