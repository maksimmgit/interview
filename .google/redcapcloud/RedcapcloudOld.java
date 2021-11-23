package pagesUserReg.redcapcloud;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class RedcapcloudOld {

    private final WebDriver driver;


    @Inject
    @Named("redcap.url")
    private String URL;

    @Inject
    private Actions actions;

    @Inject
    private JavascriptExecutor jsExecutor;

    @Inject
    public RedcapcloudOld(WebDriver driver) {
        this.driver = driver;
    }

    @Inject
    private RedcapcloudRegistration registration;


    public void goTo() {
        this.driver.get(this.URL);
    }

    public void driverQuit(){
        driver.quit();
    }

    public void fillingForm(String email, String firstName, String lastName, String password) {
        registration.clickOnRegistrationButton();
        registration.fillTheForm(email, firstName, lastName, password);

        //Вот это очень подозрительно. Не разобрался как передать драйвер правильно в registration page,
        // чтобы там прописать переход на фрейм. Брать локаторы из дочерних классов тоже выглядит так себе.
        driver.switchTo().frame(registration.iframeRecaptcha);
        registration.recaptchaStep1();
        driver.switchTo().defaultContent();
        driver.switchTo().frame(registration.iframeRecaptchaImages);
        registration.recaptchaStep2();
        driver.switchTo().defaultContent();
        driver.switchTo().frame(registration.iframeRecaptcha);
        registration.recaptchaStep3();
        driver.switchTo().defaultContent();
        registration.finishRegistration();
        jsExecutor.executeScript("arguments[0].click()", registration.buttonSignUp);
    }


}
