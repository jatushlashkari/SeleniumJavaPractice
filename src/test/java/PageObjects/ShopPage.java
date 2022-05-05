package PageObjects;

import Model.ProductModel;
import com.google.common.collect.Ordering;
import io.qameta.allure.Step;
import org.checkerframework.checker.units.qual.A;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static io.qameta.allure.internal.shadowed.jackson.databind.type.LogicalType.Collection;

public class ShopPage extends BasePageObject {

    private final By minFilterPriceSlider = By.xpath("//div[contains(@class,'price_slider')]/span[1]");
    private final By maxFilterPriceSlider = By.xpath("//div[contains(@class,'price_slider')]/span[2]");
    private final By minFilterPriceLabel = By.xpath("//div[@class='price_label']/span[1]");
    private final By maxFilterPriceLabel = By.xpath("//div[@class='price_label']/span[2]");
    private final By products = By.xpath("//li[contains(@class,'product')]");
    private final String productPriceText = "./a/span[@class='price']/ins/span[contains(@class,'amount')]";
    private final By productTitle = By.xpath("./a/h3");
    private final By productImageLink = By.xpath("./a[contains(@class,'link')]/img");
    private final By productLink = By.xpath("./a[contains(@class,'link')]");
    private final By filterButton = By.xpath("//button[text()='Filter']");
    private final By sortDropdown = By.name("orderby");
    private final By addToCartButton = By.xpath("./a[contains(@class,'product_type_simple')]");

    public ShopPage(WebDriver driver) {
        super(driver);
    }

    @Step("Set minimum filter price to '{0}'.")
    public ShopPage setMinFilterPriceTo(int minPrice) {
        Actions actions = new Actions(driver);
        WebElement slider = driver.findElement(minFilterPriceSlider);
        int currentPrice = _getMinFilterPriceText();
        if (minPrice > currentPrice) {
            int diff = minPrice - currentPrice;
            for (int i = 0; i < diff; i++) {
                actions.moveToElement(slider).click(slider).sendKeys(Keys.ARROW_RIGHT).perform();
            }
        } else if (minPrice < currentPrice) {
            int diff = currentPrice - minPrice;
            for (int i = 0; i < diff; i++) {
                actions.moveToElement(slider).click(slider).sendKeys(Keys.ARROW_LEFT).perform();
            }
        }
        services.reportLog("Set minimum filter price to '" + minPrice + "'.");
        return this;
    }

    @Step("Set maximum filter price to '{0}'.")
    public ShopPage setMaxFilterPriceTo(int maxPrice) {
        Actions actions = new Actions(driver);
        WebElement slider = driver.findElement(maxFilterPriceSlider);
        int currentPrice = _getMaxFilterPriceText();
        if (maxPrice > currentPrice) {
            int diff = maxPrice - currentPrice;
            for (int i = 0; i < diff; i++) {
                actions.moveToElement(slider).click(slider).sendKeys(Keys.ARROW_RIGHT).perform();
            }
        } else if (maxPrice < currentPrice) {
            int diff = currentPrice - maxPrice;
            for (int i = 0; i < diff; i++) {
                actions.moveToElement(slider).click(slider).sendKeys(Keys.ARROW_LEFT).perform();
            }
        }
        services.reportLog("Set maximum filter price to '" + maxPrice + "'.");
        return this;
    }

    @Step("Select product by name '{0}'")
    public ShopPage selectProductByName(String productName) {
        List<ProductModel> productModels = _getProducts();
        List<WebElement> productElements = _getProductElements();
        for (ProductModel productModel : productModels) {
            if (productModel.getProductName().equals(productName)) {
                productElements.get(productModel.getProductIndex()).click();
                break;
            }
        }
        return this;
    }

    @Step("Click on filter button.")
    public ShopPage clickFilterButton() {
        services.click(filterButton);
        services.reportLog("Click on filter button.");
        return this;
    }

    @Step("Select sort order by 'Sort by {0}'")
    public ShopPage selectSortOrderBy(String sortOrder){
        services.selectDropDownValue(sortDropdown, "Sort by "+sortOrder);
        services.reportLog("Select sort order by 'Sort by "+sortOrder+"'");
        return this;
    }

    @Step("Verify selected sorting order is 'Sort by {0}'")
    public ShopPage verifySelectedSortingOrderIs(String sortOrder){
        Assert.assertEquals(services.getSelectedDropdownValue(sortDropdown), "Sort by "+sortOrder);
        services.reportLog("Verify selected sorting order is 'Sort by "+sortOrder+"'");
        return this;
    }

    @Step("Verify filter price is applied.")
    public ShopPage verifyFilterPriceIsApplied() {
        services.waitForSeconds(5);
        int minPrice = _getMinFilterPriceText();
        int maxPrice = _getMaxFilterPriceText();
        List<ProductModel> prices = _getProducts();
        for (ProductModel productModel : prices) {
            Assert.assertTrue(productModel.getProductPrice() >= minPrice && productModel.getProductPrice() <= maxPrice);
        }
        services.reportLog("Verify filter price is applied.");
        return this;
    }

    @Step("Verify products is sort by newness.")
    public ShopPage verifySortByNewnessIsApplied(){
        List<Integer> dataIds = _getListOfDataId();
        Assert.assertTrue(Ordering.natural().reverse().isOrdered(dataIds));
        services.reportLog("Verify products is sort by newness.");
        return this;
    }

    @Step("Verify products is sort by price: low to high.")
    public ShopPage verifySortByPriceLowToHighIsApplied(){
        List<Double> listOfPrice = _getListOfPrice();
        Assert.assertTrue(Ordering.natural().isOrdered(listOfPrice));
        services.reportLog("Verify products is sort by price: low to high.");
        return this;
    }

    @Step("Verify products is sort by price: high to low.")
    public ShopPage verifySortByPriceHighToLowIsApplied(){
        List<Double> listOfPrice = _getListOfPrice();
        Assert.assertTrue(Ordering.natural().reverse().isOrdered(listOfPrice));
        services.reportLog("Verify products is sort by price: high to low.");
        return this;
    }

    public ShopPage setFilterPrice(int minPrice, int maxPrice) {
        setMinFilterPriceTo(minPrice);
        setMaxFilterPriceTo(maxPrice);
        return this;
    }

    private int _getMinFilterPriceText() {
        return Integer.parseInt(services.getText(minFilterPriceLabel).replaceAll("[^A-Za-z0-9]",""));
    }

    private int _getMaxFilterPriceText() {
        return Integer.parseInt(services.getText(maxFilterPriceLabel).replaceAll("[^A-Za-z0-9]",""));
    }

    private List<WebElement> _getProductElements() {
        return driver.findElements(products);
    }

    private List<Integer> _getListOfDataId(){
        List<ProductModel> productModels = _getProducts();
        ArrayList<Integer> orderIdList = new ArrayList<>();
        for (ProductModel productModel : productModels) {
            orderIdList.add(productModel.getProductDataId());
        }
        return orderIdList;
    }

    private List<Double> _getListOfPrice(){
        List<ProductModel> productModels = _getProducts();
        ArrayList<Double> priceList = new ArrayList<>();
        for (ProductModel productModel : productModels) {
            priceList.add(productModel.getProductPrice());
        }
        return priceList;
    }

    private List<ProductModel> _getProducts() {
        ArrayList<ProductModel> priceList = new ArrayList<>();
        List<WebElement> productElements = _getProductElements();
        int index = 0;
        for (WebElement productElement : productElements) {
            ProductModel productModel = new ProductModel();
            if (services.isSubElementVisible(productElement, By.xpath(productPriceText))) {
                WebElement element = productElement.findElement(By.xpath(productPriceText));
                productModel.setProductPrice(Double.parseDouble(element.getText().replaceAll("[^A-Za-z0-9.]","")));
            } else {
                String replace = productPriceText.replace("/ins", "");
                WebElement element = productElement.findElement(By.xpath(replace));
                productModel.setProductPrice(Double.parseDouble(element.getText().replaceAll("[^A-Za-z0-9.]","")));
            }
            productModel.setProductName(productElement.findElement(productTitle).getText());
            productModel.setProductImageUrl(productElement.findElement(productImageLink).getAttribute("src"));
            productModel.setProductLink(productElement.findElement(productLink).getAttribute("href"));
            productModel.setProductIndex(index);
            productModel.setProductDataId(Integer.parseInt(productElement.findElement(addToCartButton).getAttribute("data-product_id")));
            priceList.add(productModel);
            index++;
        }
        return priceList;
    }
}
