package Model;

public class ProductModel {
    private String productImageUrl;
    private String productName;
    private String productLink;
    private Double productPrice;
    private int productDataId;
    private int productIndex;

    public int getProductDataId() {
        return productDataId;
    }

    public void setProductDataId(int productDataId) {
        this.productDataId = productDataId;
    }

    public String getProductLink() {
        return productLink;
    }

    public void setProductLink(String productLink) {
        this.productLink = productLink;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductIndex() {
        return productIndex;
    }

    public void setProductIndex(int productIndex) {
        this.productIndex = productIndex;
    }
}
