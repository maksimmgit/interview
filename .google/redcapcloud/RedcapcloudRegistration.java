package pagesUserReg.redcapcloud;

import com.google.inject.Inject;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class RedcapcloudRegistration {

    @FindBy(xpath = "//img[contains(.,gif)]")
    WebElement gifLoading;


    @FindBy(xpath = "//div[@class='linkLabel joinBtn']")
    WebElement registrationButton;

    @FindBy(xpath = "//input[@placeholder='Email']")
    WebElement inputEmail;

    @FindBy(xpath = "//input[@placeholder='First Name']")
    WebElement inputFirstName;

    @FindBy(xpath = "//input[@placeholder='Last Name']")
    WebElement inputLastName;

    @FindBy(xpath = "//input[@placeholder='Password (8 or more characters)']")
    WebElement inputPassword;

    @FindBy(xpath = "//input[@placeholder='Confirm your password']")
    WebElement inputConfirmPassword;

    @FindBy(xpath = "//iframe[@title='reCAPTCHA']")
    WebElement iframeRecaptcha;

    @FindBy(xpath = "//iframe[contains(@src, \"bframe\")]")
    WebElement iframeRecaptchaImages;

    @FindBy(xpath = "//div[@class='recaptcha-checkbox-border']")
    WebElement recaptcha;

    @FindBy(xpath = "//button[@id=\"recaptcha-audio-button\"]")
    WebElement recaptchaAudio;

    @FindBy(xpath = "//div[@class=\"button-holder help-button-holder\"]")
    WebElement recaptchaSolve;

    @FindBy(xpath = "//span[contains(.,'Multiple ')]")
    WebElement recaptchaMultipleSolve;



    @FindBy(xpath = "//span[@aria-checked='true']")
    WebElement recaptchaPassed;

    @FindBy(xpath = "//i[@class='fa fa-square-o']")
    WebElement checkboxTerms;

    @FindBy(xpath = "//button[@class='btn btn-primary']")
    WebElement buttonSignUp;


    @Inject
    public RedcapcloudRegistration(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @Inject
    private WebDriverWait wait;

    @Inject
    private Actions actions;

    @Inject
    private JavascriptExecutor jsExecutor;

    public void clickOnRegistrationButton(){
        wait.until(invisibilityOf(gifLoading));
        wait.until(ExpectedConditions.elementToBeClickable(registrationButton));
        wait.until(visibilityOf(registrationButton));
        registrationButton.click();
    }

    public void fillTheForm(String email, String firstName, String lastName, String password){
        inputEmail.click();
        inputEmail.sendKeys(email);
        inputFirstName.click();
        inputFirstName.sendKeys(firstName);
        inputLastName.sendKeys(lastName);
        inputPassword.sendKeys(password);
        inputConfirmPassword.sendKeys(password);
    }

    public void recaptchaStep1() {
        wait.until(visibilityOf(recaptcha)).click();
    }

    public void recaptchaStep2() {
        wait.until(elementToBeClickable(recaptchaSolve));
        actions.moveToElement(recaptchaSolve)
                .pause(Duration.ofSeconds(5))
                .click()
                .perform();
        try {
            if (!recaptchaMultipleSolve.isDisplayed()) {
                recaptchaStep2();
            }
        }catch (NoSuchElementException e){
            System.out.println("Всё ок");
        }
    }

    public void recaptchaStep3(){
        wait.until(visibilityOf(recaptchaPassed));
    }

    public void finishRegistration(){
        checkboxTerms.click();
        buttonSignUp.click();
    }


}
