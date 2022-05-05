package PageObjects;

import Utils.Util;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginPage extends BasePageObject {

    private final By loginEmailInput = By.id("username");
    private final By regEmailInput = By.id("reg_email");
    private final By loginPasswordInput = By.id("password");
    private final By regPasswordInput = By.id("reg_password");
    private final By loginBtn = By.name("login");
    private final By registerBtn = By.name("register");
    private final By loginAndRegisterSection = By.id("customer_login");
    private final By loginError = By.xpath("//ul[@class='woocommerce-error']//li");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Enter login email '{0}'")
    public LoginPage enterLoginEmail(String username) {
        services.type(loginEmailInput, username);
        services.reportLog("Entered login email: '" + username + "'");
        return this;
    }

    @Step("Enter login password '{0}'")
    public LoginPage enterLoginPassword(String password) {
        services.type(loginPasswordInput, password);
        services.reportLog("Entered login password: '" + password + "'");
        return this;
    }

    @Step("Click on login button.")
    public LoginPage clickLogin() {
        services.click(loginBtn);
        services.reportLog("Clicked on login button.");
        return this;
    }

    @Step("Enter register email '{0}'")
    public LoginPage enterRegisterEmail(String email) {
        services.type(regEmailInput, email);
        services.reportLog("Entered register email: '" + email + "'");
        return this;
    }

    @Step("Enter register password '{0}'")
    public LoginPage enterRegisterPassword(String password) {
        services.typeWithJavascript(regPasswordInput, password);
        services.reportLog("Entered register password: '" + password + "'");
        return this;
    }

    @Step("Click on register button.")
    public LoginPage clickRegister() {
        services.click(registerBtn);
        services.reportLog("Clicked on register button.");
        return this;
    }

    @Step("Verify login page is displayed.")
    public LoginPage verifyLoginPageIsDisplayed() {
        services.assertElementVisible(loginAndRegisterSection, true);
        services.reportLog("Verify login page is displayed.");
        return this;
    }

    @Step("Verify login failed error message is displayed. '{0}'.")
    public LoginPage verifyLoginFailedErrorMessageDisplayed(String message) {
        services.assertElementVisible(loginError, true);
        Assert.assertTrue(services.getText(loginError).contains(message));
        services.reportLog("Verify login failed error message is displayed. '" + message + "'.");
        return this;
    }

    @Step("Verify password is masked.")
    public LoginPage verifyPasswordFieldIsMasked() {
        Assert.assertEquals(services.getAttributeValue(loginPasswordInput, "type"), "password");
        Assert.assertNotEquals(services.getText(loginPasswordInput), Util.getPassword());
        services.reportLog("Verify password is masked.");
        return this;
    }

}
