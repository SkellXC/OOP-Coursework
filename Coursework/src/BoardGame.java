
public class BoardGame extends Product{
	private int maxPlayers;
	
	public BoardGame(int productID, ProductCategory productCategory, String type, String productName,
			double price, int stock, double purchaseCost, int maxPlayers) {
		super(productID, productCategory, type, productName, price, stock, purchaseCost);
		this.maxPlayers = maxPlayers;
		
	}
	public int getMaxPlayers() {
		return maxPlayers;	
	}
	
	@Override
    public String getExtraDetails() {
        return "Max Players: " + maxPlayers;
    }
    
    @Override
    public String toString() {
        // Just append to the parent's string!
        return super.toString() + String.format("\nMax Players: %d", maxPlayers);
    }
    
    @Override
    public String toFileString() {
        return String.format("%d; board game; %s; %s; %.2f; %d; %.2f; %d",
            getProductID(), getProductType(), getProductName(), getPrice(), 
            getStock(), getPurchaseCost(), maxPlayers);
    }
}
