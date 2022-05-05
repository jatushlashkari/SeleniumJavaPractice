package Utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.function.Function;

import static ExtentReport.ExtentTestManager.getTest;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class Services {

    protected WebDriver driver;


    public Services(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForElement(By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void click(By locator) {
        driver.findElement(locator).click();

    }

    public void clickViaCss(String locator) {
        driver.findElement(By.cssSelector(locator)).click();
    }

    public void type(By locator, String text) {
        driver.findElement(locator).sendKeys(text);
    }

    public void typeWithJavascript(By locator, String text){
        WebElement inputField =  driver.findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='"+text+"';", inputField);
    }

    public void typeViaCss(String locator, String text) {
        driver.findElement(By.cssSelector(locator)).sendKeys(text);
    }

    public void type(String method, String locator, String text) {
        if (method.equalsIgnoreCase("xpath")) driver.findElement(By.xpath(locator)).sendKeys(text);
        else if (method.equalsIgnoreCase("css")) driver.findElement(By.cssSelector(locator)).sendKeys(text);
        else driver.findElement(By.id(locator)).sendKeys(text);
    }

    //Java8 way - by same method we can pass all types of locators.
    public void type(Function<String, By> locate, String locator, String text) {
        driver.findElement(locate.apply(locator)).sendKeys(text);
    }

    public void assertElementPresentByXpath(By locator) {
        Log.info("# Verifying element.");
        assertTrue(isElementPresent(locator), "Element " + locator + " not found.");
    }

    public void assertElementNotPresentByXpath(By locator) {
        Log.info("# Verifying element.");
        assertFalse(isElementPresent(locator), "Element " + locator + " is found.");
    }

    private boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isElementVisible(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isSubElementVisible(WebElement element, By locator) {
        try {
            return element.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isElementEnabled(By locator) {
        try {
            return driver.findElement(locator).isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void assertElementVisible(By locator, boolean isVisible) {
        Log.info("# Verifying element visibility.");
        if (isVisible) assertTrue(isElementVisible(locator), "Element " + locator + " should be visible.");
        else assertFalse(isElementVisible(locator), "Element " + locator + " should not be visible.");
    }

    public void waitForElementVisible(By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForElementInVisible(By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public WebElement getWebElement(By locator) {
        return driver.findElement(locator);
    }

    public String getText(By locator) {
        return driver.findElement(locator).getText();
    }

    public void selectDropDownValue(By locator, String value) {
//        waitForElementVisible(locator);
//        isElementEnabled(locator);
        Select select = new Select(driver.findElement(locator));
        select.selectByVisibleText(value);
    }

    public String getSelectedDropdownValue(By locator) {
//        waitForElementVisible(locator);
//        isElementEnabled(locator);
        Select select = new Select(driver.findElement(locator));
        return select.getFirstSelectedOption().getText();
    }

    public void reportLog(String message) {
        getTest().pass(message);//For extentTest HTML report
        Log.info(message);
    }

    public String captureScreenshot(WebDriver Driver, String TestName) {
        String screenShotDestination = null;
        try {
            Date date = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("dd_MM_yyyy");
            String TodaysDate = ft.format(date);
            TakesScreenshot ts = (TakesScreenshot) Driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            screenShotDestination = System.getProperty("user.dir") + "\\extent-reports\\screenshots\\" + TestName + "__" + TodaysDate + ".png";
            FileUtils.copyFile(source, new File(screenShotDestination));
            return screenShotDestination;
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot " + e.getMessage());
        }
        return screenShotDestination;
    }

    public void waitForSeconds(double secs) {
        try {
            Thread.sleep((long) (secs * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getAttributeValue(By locator, String attribute){
        return driver.findElement(locator).getAttribute(attribute);
    }

    public void navigateBack(){
        driver.navigate().back();
        reportLog("Back button pressed.");
    }

    public void navigateForward(){
        driver.navigate().forward();
        reportLog("Forward button pressed.");
    }

    public void pageRefresh(){
        driver.navigate().refresh();
        reportLog("Refresh button pressed.");
    }
}