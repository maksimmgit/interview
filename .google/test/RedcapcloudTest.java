package pagesUserReg.test;

import com.google.inject.Inject;
import core.DriverModule;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import pagesUserReg.redcapcloud.RedcapcloudOld;
import pagesUserReg.yandex.Yandex;

@Guice(modules = {
        DriverModule.class
})
public class RedcapcloudTest {

    @Inject
    RedcapcloudOld redcapcloudOld;

    @Inject
    Yandex yandex;

    //не разобрался как это переделать нормально
/*
    @AfterClass
    public void quit(){
        redcapcloud.driverQuit();
    }
*/

    @Test(dataProvider = "reg-test")
    public void registrationTest(String email, String firstName, String lastName, String password){
        //Идём на страницу регистрации
        //redcapcloud.goTo();
        //заполняем форму регистрации
        //redcapcloud.fillingForm(email, firstName, lastName, password);
        yandex.goTo();
    }




    @DataProvider(name = "reg-test")
    public static Object[][] getData() {
        return new Object[][]
                {
                        {"gurento08@gurenko.com",
                        "bluess",
                        "blackss",
                        "0123456789a@Ds"}
        };
    }
}
