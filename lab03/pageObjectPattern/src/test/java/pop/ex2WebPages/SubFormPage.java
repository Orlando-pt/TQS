package pop.ex2WebPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SubFormPage {

    private WebDriver driver;

    @FindBy(css = "h2")
    private WebElement heading;

    @FindBy(id = "inputName")
    private WebElement inputName;

    @FindBy(id = "zipCode")
    private WebElement zipCode;

    @FindBy(id = "creditCardNumber")
    private WebElement creditCardNumber;

    @FindBy(css = ".btn-primary")
    private WebElement submitButton;

    public SubFormPage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    public void setInputName(String name) {
        this.inputName.clear();
        this.inputName.sendKeys(name);
    }

    public void setZipCode(String zipcode) {
        this.zipCode.clear();
        this.zipCode.sendKeys(zipcode);
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber.clear();
        this.creditCardNumber.sendKeys(creditCardNumber);
    }

    public void submitForm() {
        this.submitButton.click();
    }

    public boolean isOnPage() {
        return this.heading.getText().equals("Your flight from TLV to SFO has been reserved.");
    }
}
