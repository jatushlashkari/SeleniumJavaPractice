package PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePageObject{

    public HomePage(WebDriver driver) {
        super(driver);
    }

    private final String menuItemXpath = "//li[contains(@id,'menu-item')]/a[text()='TEST']";

    @Step("Select menu '{0}'")
    public HomePage selectMenuItem(String menuItem){
        String dynamicXpath = menuItemXpath.replace("TEST",menuItem);
        services.click(By.xpath(dynamicXpath));
        services.reportLog("Select "+ menuItem+" menu.");
        return this;
    }
}
