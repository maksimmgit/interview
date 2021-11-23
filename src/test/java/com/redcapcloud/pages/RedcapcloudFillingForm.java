package com.redcapcloud.pages;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.redcapcloud.core.InjectLogger;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.File;
import java.time.Duration;

public class RedcapcloudFillingForm {

    @InjectLogger
    Logger logger;

    @Inject
    @Named("redcap.fullname")
    private String FULLNAME;

    @Inject
    @Named("redcap.login")
    private String EMAIL;

    @Inject
    @Named("redcap.login")
    private String LOGIN;

    @Inject
    @Named("redcap.password")
    private String PASSWORD;

    @Inject
    @Named("redcap.date.value")
    private String DATE;

    @Inject
    @Named("redcap.dropdown.value")
    private String DROPDOWN;

    @Inject
    @Named("redcap.image")
    private String IMAGE;

    @FindBy(xpath = "//input[@id='name']")
    WebElement inputName;

    @FindBy(xpath = "//div[@class='gwt-Label'][text()='male']")
    WebElement radioMale;

    @FindBy(xpath = "//div[@class='gwt-HTML'][contains(.,'Yes')]")
    WebElement checkboxYES;

    @FindBy(xpath = "//table[@class='dateInput']")
    WebElement tableInputLink;

    @FindBy(xpath = "//input[@id='dropdown_input']")
    WebElement inputDropDown;

    @FindBy(xpath = "//input[@name='uploadFormElement']")
    WebElement imageUploader;

    @FindBy(xpath = "//i[@id='fileInput_removeFile']")
    WebElement uploadCheck;

    @FindBy(xpath = "//span[@class='ui-slider-handle ui-corner-all ui-state-default']")
    WebElement slider;

    @FindBy(xpath = "//img[contains(@src, 'image.jpg')]")
    WebElement imageChecker;

    @FindBy(xpath = "//div[@responsive-label='Column1']/parent::td/preceding-sibling::td/span/input[@name='rdio_1']")
    WebElement radioColumnOneFirst;

    @FindBy(xpath = "//div[@responsive-label='Column2']/parent::td/preceding-sibling::td/span/input[@name='rdio_2']")
    WebElement radioColumnTwoSecond;

    @FindBy(xpath = "//div[@class='myHealthButtonsBottom']/child::button[@class='btn btn-default cancelButton']")
    WebElement cancelButton;

    @FindBy(xpath = "//div[@class='crfStatusAvailable' and contains(., 'Not Started')]")
    WebElement notStarted;

    @Inject
    public RedcapcloudFillingForm(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @Inject
    private WebDriverWait wait;

    @Inject
    private Actions actions;

    @Inject
    private JavascriptExecutor jsExecutor;

    public void fillingTheForm(){
        logger.debug("Waiting until the form is available");
        logger.info("Filling the form with the name {} and email {}", inputName, EMAIL);
        wait.until(ExpectedConditions.visibilityOf(inputName));
        wait.until(ExpectedConditions.elementToBeClickable(inputName));
        actions.moveToElement(inputName)
                .click()
                .sendKeys(FULLNAME)
                .sendKeys(Keys.TAB)
                .sendKeys(EMAIL)
                .perform();
        actions.moveToElement(radioMale)
                .click()
                .perform();

        logger.debug("Waiting until checkbox is available");
        wait.until(ExpectedConditions.elementToBeClickable(checkboxYES)).click();
        wait.until(ExpectedConditions.elementToBeClickable(tableInputLink));
        logger.info("Filling the date with {} value", DATE);
        actions.moveToElement(tableInputLink)
                .click()
                .sendKeys(DATE)
                .sendKeys(Keys.ENTER)
                .perform();
        logger.info("Filling the dropdown menu with {} value", DROPDOWN);
        actions.moveToElement(inputDropDown)
                .click()
                .sendKeys(DROPDOWN)
                .sendKeys(Keys.ENTER)
                .perform();
    }

    public void fillingTheTableGroupTwoUploader(){
        logger.debug("Uploading an image");
        File file = new File(IMAGE);
        String imageAbsolute = file.getAbsolutePath();
        logger.info("Using {} path as absolute", imageAbsolute);
        jsExecutor.executeScript("arguments[0].scrollIntoView();",imageUploader);
        imageUploader.sendKeys(imageAbsolute);

        try{
            logger.debug("Checking if an image is uploaded");
            wait.until(ExpectedConditions.visibilityOf(uploadCheck));
            imageChecker.isDisplayed();
        }catch (NoSuchElementException e){
            logger.debug("Repeat uploading");
            fillingTheTableGroupTwoUploader();
        }

        logger.debug("Interacting with the slider");
        jsExecutor.executeScript("arguments[0].setAttribute('style','left: 79%;')", slider);

    }

    public void fillingMatrix(){
        logger.debug("Interacting with the matrix buttons");
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", radioColumnOneFirst);
        actions.moveToElement(radioColumnOneFirst)
                .click()
                .moveToElement(radioColumnTwoSecond)
                .click()
                .pause(Duration.ofSeconds(4))
                .moveToElement(cancelButton)
                .click()
                .perform();
    }
    public String notStarted(){
        logger.debug("Returned to the application page");
        return notStarted.getText();
    }

}
