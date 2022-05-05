package TestCases;

import Utils.Util;
import org.testng.annotations.Test;

public class RegisterTest extends BaseTest{

    @Test(description = "Registration with valid email and valid password")
    public void TC01_RegistrationWithValidEmailAndValidPassword() {
        String randomEmail = Util.getRandomEmail();
        homePage.selectMenuItem("My Account");
        loginPage.verifyLoginPageIsDisplayed()
                .enterRegisterEmail(randomEmail)
                .enterRegisterPassword(Util.getRandomString(16))
                .clickRegister();
        myAccountPage.verifyUserIsLoggedIn(randomEmail);
    }

    @Test(description = "Registration with invalid email and valid password")
    public void TC02_RegistrationWithInvalidEmailAndValidPassword() {
        String invalidEmail = Util.getRandomString(5)+"@"+Util.getRandomString(5);
        homePage.selectMenuItem("My Account");
        loginPage.verifyLoginPageIsDisplayed()
                .enterRegisterEmail(invalidEmail)
                .enterRegisterPassword(Util.getRandomString(16))
                .clickRegister()
                .verifyLoginFailedErrorMessageDisplayed("Please provide a valid email address.");
    }

    @Test(description = "Registration with empty email and valid password")
    public void TC03_RegistrationWithEmptyEmailAndValidPassword() {
        homePage.selectMenuItem("My Account");
        loginPage.verifyLoginPageIsDisplayed()
                .enterRegisterEmail("")
                .enterRegisterPassword(Util.getRandomString(16))
                .clickRegister()
                .verifyLoginFailedErrorMessageDisplayed("Please provide a valid email address.");
    }

    @Test(description = "Registration with valid email and empty password")
    public void TC04_RegistrationWithValidEmailAndEmptyPassword() {
        String randomEmail = Util.getRandomEmail();
        homePage.selectMenuItem("My Account");
        loginPage.verifyLoginPageIsDisplayed()
                .enterRegisterEmail(randomEmail)
                .enterRegisterPassword("")
                .clickRegister()
                .verifyLoginFailedErrorMessageDisplayed("Please enter an account password.");
    }

    @Test(description = "Registration with empty email and empty password")
    public void TC05_RegistrationWithEmptyEmailAndEmptyPassword() {
        homePage.selectMenuItem("My Account");
        loginPage.verifyLoginPageIsDisplayed()
                .enterRegisterEmail("")
                .enterRegisterPassword("")
                .clickRegister()
                .verifyLoginFailedErrorMessageDisplayed("Please provide a valid email address.");
    }

}
