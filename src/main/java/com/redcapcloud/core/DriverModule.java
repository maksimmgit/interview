package com.redcapcloud.core;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DriverModule extends AbstractModule {


    @Override
    protected void configure() {

        //DriverManager config
        bind(DriverManager.class)
                .to(ChromeDriverManager.class)
                .in(Scopes.SINGLETON);

        //My test input properties
        try(FileInputStream file = new FileInputStream("src/test/resources/uat.properties")) {
            Properties props = new Properties();
            props.load(file);
            Names.bindProperties(binder(), props);
        } catch (IOException e) {
            //skip
        }
        bindListener(Matchers.any(), new Log4JTypeListener());

    }
    @Provides
    @Singleton
    public WebDriver getDriver(DriverManager driverManager) {
        return driverManager.getDriver();
    }

    @Provides
    public Actions getActions(WebDriver driver) {
        return new Actions(driver);
    }

    @Provides
    public JavascriptExecutor getExecutor(WebDriver driver) {
        return (JavascriptExecutor)(driver);
    }

    @Provides
    public WebDriverWait getWebDriverWait(WebDriver driver){
        return new WebDriverWait(driver, 15);
    }

}
