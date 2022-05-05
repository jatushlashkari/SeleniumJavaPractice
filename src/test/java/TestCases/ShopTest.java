package TestCases;

import Utils.Util;
import org.testng.annotations.Test;

public class ShopTest extends BaseTest{

    @Test(description = "Shop filter by price functionality.")
    public void TC01_ShopFilterByPriceFunctionality() {
        homePage.selectMenuItem("Shop");
        shopPage.setFilterPrice(150, 450)
                .clickFilterButton()
                .verifyFilterPriceIsApplied();
    }

    @Test(description = "Shop product categories functionality.")
    public void TC02_ShopProductCategoriesFunctionality() {
        String product = "Thinking in HTML";
        homePage.selectMenuItem("Shop");
        shopPage.selectProductByName(product);
        productPage.verifyProductPageIsDisplayed()
                .verifyProductTitleIs(product);
    }

    @Test(description = "Shop sort order by newness functionality.")
    public void TC03_ShopSortOrderByNewnessFunctionality() {
        homePage.selectMenuItem("Shop");
        shopPage.selectSortOrderBy("newness")
                .verifySelectedSortingOrderIs("newness")
                .verifySortByNewnessIsApplied();
    }

    @Test(description = "Shop sort order by price: low to high functionality.")
    public void TC04_ShopSortOrderByPriceLowToHighFunctionality() {
        homePage.selectMenuItem("Shop");
        shopPage.selectSortOrderBy("price: low to high")
                .verifySelectedSortingOrderIs("price: low to high")
                .verifySortByPriceLowToHighIsApplied();
    }

    @Test(description = "Shop sort order by price: high to low functionality.")
    public void TC05_ShopSortOrderByPriceHighToLowFunctionality() {
        homePage.selectMenuItem("Shop");
        shopPage.selectSortOrderBy("price: high to low")
                .verifySelectedSortingOrderIs("price: high to low")
                .verifySortByPriceHighToLowIsApplied();
    }

}
