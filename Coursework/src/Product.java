
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
    
}
