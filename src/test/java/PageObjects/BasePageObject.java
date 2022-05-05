package PageObjects;

import Utils.Services;
import org.openqa.selenium.WebDriver;

public class BasePageObject {

    public Services services;
    public WebDriver driver;

    public BasePageObject(WebDriver driver) {
        this.driver = driver;
        services = new Services(driver);
    }
}
