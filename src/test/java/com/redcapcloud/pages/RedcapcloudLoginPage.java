package com.redcapcloud.pages;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.redcapcloud.core.InjectLogger;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class RedcapcloudLoginPage {

    @InjectLogger
    Logger logger;


    @Inject
    @Named("redcap.login")
    private String LOGIN;

    @Inject
    @Named("redcap.password")
    private String PASSWORD;

    @FindBy(xpath = "//input[@id=\"login_username\"]")
    WebElement inputEmail;


    @FindBy(xpath = "//input[@id=\"login_password\"]")
    WebElement inputPassword;

    @FindBy(xpath = "//div[@class='card card-4']")
    WebElement interviewClass;

    @FindBy(xpath = "//div[@class='studyForm']//div[@class='linkLabel']")
    WebElement linkDemo;

    @Inject
    public RedcapcloudLoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @Inject
    private WebDriverWait wait;

    @Inject
    private Actions actions;

    @Inject
    private JavascriptExecutor jsExecutor;

    public void login(){
        logger.debug("Login step");
        logger.info("Email {}", LOGIN);
        wait.until(ExpectedConditions.visibilityOf(inputEmail)).sendKeys(LOGIN);
        inputEmail.sendKeys(Keys.ENTER);
        logger.info("PASSWORD {}", PASSWORD);
        wait.until(ExpectedConditions.elementToBeClickable(inputPassword)).sendKeys(PASSWORD);
        inputPassword.sendKeys(Keys.ENTER);
    }

    public void proceedToForm(){
        logger.debug("Proceeding to form");
        wait.until(ExpectedConditions.elementToBeClickable(interviewClass)).click();
        wait.until(ExpectedConditions.elementToBeClickable(linkDemo)).click();
    }

}
