public class BoardGame extends Product {
    private String type;
    private int maxPlayers;

    public BoardGame(int productId, ProductCategory productCategory, String type, String productName, 
            double purchaseCost, int quantityInStock, double price, int maxPlayers) {
        super(productId, productCategory, productName, purchaseCost, quantityInStock, price);
        this.type = type;
        this.maxPlayers = maxPlayers;
    }

    public String getType() {
        return type;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    @Override
    public String toString() {
        return String.format("%-6d | %-30s | £%-7.2f | %-7d | Max Players: %d", 
                getProductId(), getProductName(), getPrice(), getQuantityInStock(), maxPlayers);
    }
}