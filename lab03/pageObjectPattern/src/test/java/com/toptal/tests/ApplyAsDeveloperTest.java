package com.toptal.tests;

import com.toptal.webpages.DeveloperApplyPage;
import com.toptal.webpages.HomePage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class ApplyAsDeveloperTest {

    private WebDriver driver;

    @BeforeEach
    public void setup() {
        // use FF driver
        this.driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterEach
    public void close() {
        this.driver.close();
    }

    @Test
    public void applyAsDeveloper() {
        // Create object of HomePage Class
        HomePage home = new HomePage(driver);
        home.clickOnDeveloperApplyButton();

        // Create object of DeveloperApplyPage
        DeveloperApplyPage applyPage = new DeveloperApplyPage(driver);

        // Check if page is opened
        Assertions.assertTrue(applyPage.isPageOpened());

        // Fill up data
        applyPage.setDeveloper_email("dejan@toptal.com");
        applyPage.setDeveloper_full_name("Dejan Zivanovic Automated Test");
        applyPage.setDeveloper_password("password123");
        applyPage.setDeveloper_password_confirmation("password123");

        // Click on Join
        //applyPage.clickOnJoin();
    }
}
