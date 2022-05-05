package TestCases;

import Utils.Util;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(description = "Login with valid username and valid password")
    public void TC01_LoginWithValidUsernameAndValidPassword() {
        homePage.selectMenuItem("My Account");
        loginPage.verifyLoginPageIsDisplayed()
                .enterLoginEmail(Util.getUsername())
                .enterLoginPassword(Util.getPassword())
                .clickLogin();
        myAccountPage.verifyUserIsLoggedIn(Util.getUsername());
    }

    @Test(description = "Login with invalid username and invalid password")
    public void TC02_LoginWithInvalidUsernameAndInvalidPassword() {
        homePage.selectMenuItem("My Account");
        loginPage.verifyLoginPageIsDisplayed()
                .enterLoginEmail(Util.getRandomEmail())
                .enterLoginPassword(Util.getRandomString(8))
                .clickLogin()
                .verifyLoginFailedErrorMessageDisplayed("A user could not be found with this email address.");
    }

    @Test(description = "Login with valid username and empty password")
    public void TC03_LoginWithValidUsernameAndEmptyPassword() {
        homePage.selectMenuItem("My Account");
        loginPage.verifyLoginPageIsDisplayed()
                .enterLoginEmail(Util.getUsername())
                .enterLoginPassword("")
                .clickLogin()
                .verifyLoginFailedErrorMessageDisplayed("Password is required.");
    }

    @Test(description = "Login with empty username and valid password")
    public void TC04_LoginWithEmptyUsernameAndValidPassword() {
        homePage.selectMenuItem("My Account");
        loginPage.verifyLoginPageIsDisplayed()
                .enterLoginEmail("")
                .enterLoginPassword(Util.getPassword())
                .clickLogin()
                .verifyLoginFailedErrorMessageDisplayed("Username is required.");
    }

    @Test(description = "Login with empty username and empty password")
    public void TC05_LoginWithEmptyUsernameAndEmptyPassword() {
        homePage.selectMenuItem("My Account");
        loginPage.verifyLoginPageIsDisplayed()
                .enterLoginEmail("")
                .enterLoginPassword("")
                .clickLogin()
                .verifyLoginFailedErrorMessageDisplayed("Username is required.");
    }

    @Test(description = "Login password should be masked.")
    public void TC06_LoginPasswordShouldBeMasked() {
        homePage.selectMenuItem("My Account");
        loginPage.verifyLoginPageIsDisplayed()
                .enterLoginPassword(Util.getPassword())
                .verifyPasswordFieldIsMasked();
    }

    @Test(description = "Login handles case sensitive.")
    public void TC07_LoginHandlesCaseSensitive() {
        homePage.selectMenuItem("My Account");
        loginPage.verifyLoginPageIsDisplayed()
                .enterLoginEmail(Util.getUsername().toUpperCase())
                .enterLoginPassword(Util.getPassword().toUpperCase())
                .clickLogin()
                .verifyLoginFailedErrorMessageDisplayed("The password you entered for the username "+Util.getUsername().toUpperCase()+" is incorrect.");
    }

    @Test(description = "Login authentication.")
    public void TC08_LoginAuthentication() {
        homePage.selectMenuItem("My Account");
        loginPage.verifyLoginPageIsDisplayed()
                .enterLoginEmail(Util.getUsername())
                .enterLoginPassword(Util.getPassword())
                .clickLogin();
        myAccountPage.verifyUserIsLoggedIn(Util.getUsername())
                .clickOnSignOut();
        loginPage.verifyLoginPageIsDisplayed();
        services.navigateBack();
        loginPage.verifyLoginPageIsDisplayed();

    }

}
