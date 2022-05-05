package PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class ProductPage extends BasePageObject{

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    private final By content = By.id("content");
    private final By productTitle = By.className("product_title");
    private final String productPrice = "//p[@class='price']/ins/span[@class='woocommerce-Price-amount amount']";

    @Step("Verify product page is displayed.")
    public ProductPage verifyProductPageIsDisplayed(){
        services.assertElementVisible(content, true);
        services.reportLog("Verify product page is displayed.");
        return this;
    }

    @Step("Verify product title is '{0}'")
    public ProductPage verifyProductTitleIs(String productName){
        Assert.assertEquals(services.getText(productTitle), productName);
        services.reportLog("Verify product title is '"+productName+"'");
        return this;
    }

}
