package selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.*;

class SampleTest {

    WebDriver browser;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        browser = new FirefoxDriver();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        browser.close();
    }

    @Test
    public void site_header_is_on_page() {

        browser.get("https://www.saucelabs.com");
        WebElement href = browser.findElement(By.xpath("//a[@href='https://accounts.saucelabs.com/']"));
        assertTrue((href.isDisplayed()));
    }

    @Test
    public void test_selenium_ide_export() {
        browser.get("https://blazedemo.com/");
        browser.manage().window().setSize(new Dimension(550, 692));
        browser.findElement(By.name("fromPort")).click();
        browser.findElement(By.cssSelector(".form-inline:nth-child(1) > option:nth-child(1)")).click();
        browser.findElement(By.name("toPort")).click();
        {
            WebElement dropdown = browser.findElement(By.name("toPort"));
            dropdown.findElement(By.xpath("//option[. = 'London']")).click();
        }
        browser.findElement(By.cssSelector(".form-inline:nth-child(4) > option:nth-child(3)")).click();
        browser.findElement(By.cssSelector(".btn-primary")).click();
        assertEquals("Flights from Paris to London:", browser.findElement(By.cssSelector("h3")).getText());
        browser.findElement(By.cssSelector("tr:nth-child(2) .btn")).click();
        browser.findElement(By.id("inputName")).click();
        browser.findElement(By.id("inputName")).sendKeys("António José");
        browser.findElement(By.id("zipCode")).click();
        browser.findElement(By.id("zipCode")).sendKeys("3333");
        browser.findElement(By.id("cardType")).click();
        browser.findElement(By.id("cardType")).click();
        {
            WebElement dropdown = browser.findElement(By.id("cardType"));
            dropdown.findElement(By.xpath("//option[. = 'American Express']")).click();
        }
        browser.findElement(By.cssSelector("option:nth-child(2)")).click();
        browser.findElement(By.id("creditCardNumber")).click();
        browser.findElement(By.id("creditCardNumber")).sendKeys("1231313131312312312312");
        browser.findElement(By.cssSelector(".btn-primary")).click();
        assertEquals("BlazeDemo Confirmation", browser.getTitle());
    }
}