public class Accessory extends Product {
    private String type;
    private String compatibility;

    public Accessory(int productId, ProductCategory productCategory, String type, String productName, 
            double purchaseCost, int quantityInStock, double price, String compatibility) {
        super(productId, productCategory, productName, purchaseCost, quantityInStock, price);
        this.type = type;
        this.compatibility = compatibility;
    }

    public String getType() {
        return type;
    }

    public String getCompatibility() {
        return compatibility;
    }

    @Override
    public String toString() {
        return String.format("%-6d | %-30s | £%-7.2f | %-7d | Compatibility: %s", 
                getProductId(), getProductName(), getPrice(), getQuantityInStock(), compatibility);
    }
}