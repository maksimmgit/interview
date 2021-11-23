package com.redcapcloud.pages.test;

import com.google.inject.Inject;
import com.redcapcloud.core.DriverModule;
import com.redcapcloud.core.InjectLogger;
import com.redcapcloud.pages.Redcapcloud;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

@Guice(modules = {
        DriverModule.class
})
public class TestRedcapcloudTest {

    @InjectLogger
    Logger logger;

    @Inject
    Redcapcloud redcapcloud;

    //не разобрался как это переделать нормально
    @AfterClass
    public void quit(){
        redcapcloud.driverQuit();
    }

    @Test
    public void loginTest(){
        redcapcloud.goTo();

        redcapcloud.getLoginPage().login();
        redcapcloud.getLoginPage().proceedToForm();

        redcapcloud.getFillingForm().fillingTheForm();
        redcapcloud.getFillingForm().fillingTheTableGroupTwoUploader();
        redcapcloud.getFillingForm().fillingMatrix();

        Assert.assertTrue(redcapcloud.getFillingForm().notStarted().equalsIgnoreCase("not started"));
    }

}

