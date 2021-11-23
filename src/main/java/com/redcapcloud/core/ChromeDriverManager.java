package com.redcapcloud.core;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class ChromeDriverManager extends DriverManager{

    private ChromeDriverService chService;


    @Override
    protected void startService() {
        if (null == chService) {
            try {
                chService = new ChromeDriverService.Builder()
                        .usingDriverExecutable(new File("src/test/resources/chromedriver.exe"))
                        .usingAnyFreePort()
                        .build();
                chService.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void stopService() {
        if (null != chService && chService.isRunning())
            chService.stop();
    }

    @Override
    public void quitDriver() {
        super.quitDriver();
    }

    @Override
    protected void createDriver() {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-features=EnableEphemeralFlashPermission");
        options.addArguments("--mute-audio");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-blink-features");
        options.addArguments("--start-maximized");
        //options.addArguments("--user-data-dir=C:/Users/max/IdeaProjects/GuiceMaven/GoogleProfile");
        //options.addArguments("--profile-directory=Profile 3");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(chService, options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20L, TimeUnit.SECONDS);


    }
}
