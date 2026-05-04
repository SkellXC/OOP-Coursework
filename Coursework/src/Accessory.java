
public class Accessory extends Product{
	private String compatibility;
	
	public Accessory(int productID, ProductCategory productCategory, String type, String productName,
			double price, int stock, double purchaseCost, String compatibility) {
		super(productID, productCategory, type, productName, price, stock, purchaseCost);
		this.compatibility = compatibility;
	}
	public String getCompatibility() {
		return compatibility;
	}
	
	// NEW: Provide the specific detail for the CLI table
    @Override
    public String getExtraDetails() {
        return "Compatibility: " + compatibility;
    }
    
    @Override
    public String toString() {
        // Just append to the parent's string!
        return super.toString() + String.format("\nCompatibility: %s", compatibility);
    }
    
    @Override
    public String toFileString() {
        return String.format("%d; accessory; %s; %s; %.2f; %d; %.2f; %s",
            getProductID(), getProductType(), getProductName(), getPrice(), 
            getStock(), getPurchaseCost(), compatibility);
    }
}	

