package TestCases;

import Base.BaseClass;
import PageObjects.*;
import Utils.Log;
import Utils.Services;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.lang.reflect.Method;

import static ExtentReport.ExtentTestManager.startTest;

public class BaseTest extends BaseClass {

    public WebDriver driver;
    public HomePage homePage;
    public LoginPage loginPage;
    public MyAccountPage myAccountPage;
    public Services services;
    public ShopPage shopPage;
    public ProductPage productPage;

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void suitSetup(Method method, String browser) {
        driver = setupDriver(browser);
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        myAccountPage = new MyAccountPage(driver);
        shopPage = new ShopPage(driver);
        productPage = new ProductPage(driver);
        services = new Services(driver);
        startTest(method.getName(), method.getAnnotation(Test.class).description()).assignAuthor("Jatush").assignDevice(browser).assignCategory("Smoke");
        Log.info("=================================================================");
        Log.info("TEST STARTED # "+method.getAnnotation(Test.class).priority()+" - " + method.getAnnotation(Test.class).description());
        Log.info("=================================================================");
    }

    @AfterMethod(alwaysRun = true)
    public void driverClose(Method method) {
        tearDown();
        Log.info("=================================================================");
        Log.info("TEST COMPLETED # "+method.getAnnotation(Test.class).priority()+" - " + method.getAnnotation(Test.class).description());
        Log.info("=================================================================");

    }

    @AfterSuite
    public void sendEmail(){
        Log.info("Sending reports to email...");
//        Util.sendReportsInEmail(properties);
    }

}
