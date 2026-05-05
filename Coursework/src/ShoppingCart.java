import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<Product> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public ServiceResult addItemToCart(Product product, int quantity) {
        if (quantity < 1) return ServiceResult.INVALID_INPUT;
        
        int currentInCart = getProductCount(product.getProductId());
        if ((currentInCart + quantity) > product.getQuantityInStock()) {
            return ServiceResult.INSUFFICIENT_QUANTITY;
        }
        
        for (int i = 0; i < quantity; i++) items.add(product);
        return ServiceResult.SUCCESS;
    }

    public int getProductCount(int productId) {
        int count = 0;
        for (Product item : items) {
            if (item.getProductId() == productId) count++;
        }
        return count;
    }

    public ServiceResult removeItemFromCart(Product product, int quantity) {
        int currentCount = getProductCount(product.getProductId());
        if (currentCount == 0) return ServiceResult.NOT_FOUND;
        if (quantity > currentCount) return ServiceResult.EXCEEDED_QUANTITY;
        
        for (int i = 0; i < quantity; i++) items.remove(product);
        return ServiceResult.SUCCESS;
    }

    public void clearCart() {
        items.clear();
    }

    public double calculateTotal() {
        double total = 0;
        for(Product item : items) total += item.getPrice();
        return total;
    }

    public ArrayList<Product> getItems() {
        return items;
    }

    public Receipt pay(PaymentMethod paymentMethod, Stock mainStock, Address billingAddress) {
        if (this.items.isEmpty()) return null; 

        ArrayList<Integer> processedIDs = new ArrayList<>();
        
        for (Product basketItem : this.items) {
            int id = basketItem.getProductId();
            
            if (!processedIDs.contains(id)) {
                int totalQtyInBasket = getProductCount(id);
                Product stockItem = mainStock.findProductById(id);
                
                if (stockItem == null || totalQtyInBasket > stockItem.getQuantityInStock()) return null; 
                processedIDs.add(id);
            }
        }

        double totalAmount = calculateTotal();
        for (Integer id : processedIDs) {
            int totalQtyInBasket = getProductCount(id);
            mainStock.updateStockValue(id, -totalQtyInBasket);
        }
        
        Receipt finalReceipt = paymentMethod.processPayment(totalAmount, billingAddress);
        this.clearCart();
        return finalReceipt;
    }
}