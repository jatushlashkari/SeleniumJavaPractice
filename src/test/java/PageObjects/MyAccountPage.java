package PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class MyAccountPage extends BasePageObject {

    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    private final By usernameText = By.xpath("//div[@class='woocommerce-MyAccount-content']/p[1]/strong");
    private final By signOutLink = By.xpath("//a[text()='Sign out']");

    @Step("Verify '{0}' is logged in.")
    public MyAccountPage verifyUserIsLoggedIn(String username){
        Assert.assertTrue(driver.getTitle().contains("My Account"));
        Assert.assertEquals(services.getText(usernameText),username.split("@")[0]);
        services.reportLog("Verify '"+username+"' logged in successfully.");
        return this;
    }

    @Step("Click on sign out link.")
    public MyAccountPage clickOnSignOut(){
        services.click(signOutLink);
        services.reportLog("Click on sign out link.");
        return this;
    }

}
