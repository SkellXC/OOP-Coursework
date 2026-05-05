public abstract class Product {
    private int productId;
    private ProductCategory productCategory;
    private String productName;
    private double purchaseCost;
    private int quantityInStock;
    private double price;

    public Product(int productId, ProductCategory productCategory, String productName, 
            double purchaseCost, int quantityInStock, double price) {
        this.productId = productId;
        this.productCategory = productCategory;
        this.productName = productName;
        this.purchaseCost = purchaseCost;
        this.quantityInStock = quantityInStock;
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public String getProductName() {
        return productName;
    }

    public double getPurchaseCost() {
        return purchaseCost;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public abstract String toString();
}