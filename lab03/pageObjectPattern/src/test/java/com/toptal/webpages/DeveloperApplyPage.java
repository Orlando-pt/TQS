package com.toptal.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DeveloperApplyPage {

    private WebDriver driver;

    @FindBy(tagName = "h1")
    private WebElement heading;

    @FindBy(id = "talent_create_applicant_full_name")
    private WebElement developer_full_name;

    @FindBy(id = "talent_create_applicant_email")
    private WebElement developer_email;

    @FindBy(id = "talent_create_applicant_password")
    private WebElement developer_password;

    @FindBy(id = "talent_create_applicant_password_confirmation")
    private WebElement developer_password_confirmation;

    @FindBy(id = "save_new_talent_create_applicant")
    private WebElement save_button;

    // Constructor
    public DeveloperApplyPage(WebDriver driver) {
        this.driver = driver;

        // Initialize elements
        PageFactory.initElements(driver, this);
    }

    public void setDeveloper_email(String email) {
        this.developer_email.clear();

        this.developer_email.sendKeys(email);
    }

    public void setDeveloper_password(String password) {
        this.developer_password.clear();

        this.developer_password.sendKeys(password);
    }

    public void setDeveloper_password_confirmation(String password) {
        this.developer_password_confirmation.clear();

        this.developer_password_confirmation.sendKeys(password);
    }

    public void setDeveloper_full_name(String full_name) {
        this.developer_full_name.clear();

        this.developer_full_name.sendKeys(full_name);
    }

    public void clickOnJoin() {
        this.save_button.click();
    }

    public boolean isPageOpened() {
        // Assertion
        return this.heading.getText().toString().contains("Apply to Join the World's Top Talent Network");
    }
}
