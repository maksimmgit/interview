package com.redcapcloud.pages;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class Redcapcloud {

    private final WebDriver driver;

    @Inject
    @Named("redcap.main.url")
    private String URL;

    @Inject
    private Actions actions;

    @Inject
    private JavascriptExecutor jsExecutor;

    @Inject
    public Redcapcloud(WebDriver driver) {
        this.driver = driver;
    }

    @Inject
    private RedcapcloudLoginPage loginPage;

    @Inject
    private RedcapcloudFillingForm fillingForm;

    public void goTo() {
        this.driver.get(this.URL);
    }

    public void driverQuit(){
        driver.quit();
    }

    public RedcapcloudLoginPage getLoginPage() {
        return loginPage;
    }

    public RedcapcloudFillingForm getFillingForm() {
        return fillingForm;
    }
}
