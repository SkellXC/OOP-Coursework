import java.util.Objects;

public abstract class Product {
	private int productID;
	private ProductCategory productCategory;
	private String type;
	private String productName;
	private double purchaseCost;
	private double price;
	private int stock;

	public Product(int productID, ProductCategory productCategory, String type, 
			String productName,double price, int stock, double purchaseCost) {
		this.productID = productID;
		this.productCategory = productCategory;
		this.type = type;
		this.productName = productName;
		this.purchaseCost = purchaseCost;
		this.price = price;
		this.stock = stock;
	}
	
	public String getExtraDetails() {
        return ""; 
    }
	
	public int getProductID() {
        return productID;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }
    
    public String getProductType() {
    	return type;
    }

    public String getProductName() {
        return productName;
    }

    public double getPurchaseCost() {
        return purchaseCost;
    }
    public double getPrice() {
    	return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int newStockAmount) {
    	this.stock = newStockAmount;
    }
    
    public void setPrice(double newPrice) {
    	this.price = newPrice;
    }

    @Override
    public String toString() {
        return String.format("ID: %d "
                + "\nCategory: %s  "
                + "\nType: %s"
                + "\nName: %s"
                + "\nPrice: £%.2f"
                + "\nStock: %d",
                getProductID(), getProductCategory(), getProductType(), getProductName(), getPrice(), getStock());
    }
    
    public abstract String toFileString();
    
    @Override
    public boolean equals(Object obj) {
        // 1. If it's the exact same memory reference, it's equal
        if (this == obj) return true;
        
        // 2. If the other object is null or not a Product, it's not equal
        if (obj == null || getClass() != obj.getClass()) return false;
        
        // 3. Cast the object to a Product and compare their IDs
        Product otherProduct = (Product) obj;
        return this.productID == otherProduct.productID;
    }
    
    @Override
    public int hashCode() {
        // Whenever you override equals, you MUST override hashCode.
        // It ensures data structures like HashMaps can find your object.
        return Objects.hash(productID);
    }
    
    
}
