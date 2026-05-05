import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

	// Key = Product, Value = Quantity
	private Map<Product, Integer> items;
	public ShoppingCart() {
		this.items = new HashMap<>();
	}
	

	public ServiceResult addItemToCart(Product product, int quantity) {
        if (quantity < 1) {
            return ServiceResult.INVALID_INPUT;
        }
        
        int currentInCart = getProductCount(product);
        if ((currentInCart + quantity) > product.getStock()) {
            return ServiceResult.INSUFFICIENT_QUANTITY;
        }
        
        // Correct Map syntax for adding
        items.put(product, currentInCart + quantity);
        return ServiceResult.SUCCESS;
    }

	
	public int getProductCount(Product product) {
        return items.getOrDefault(product, 0);
    }

	public ServiceResult removeItemFromCart(Product product, int quantity) {
        int currentCount = getProductCount(product);
        
        if (currentCount == 0) {
            return ServiceResult.NOT_FOUND;
        }
        if (quantity > currentCount) {
            return ServiceResult.EXCEEDED_QUANTITY;
        }
        
        int newQuantity = currentCount - quantity;
        
        if (newQuantity <= 0) {
            items.remove(product);
        } else {
            items.put(product, newQuantity);
        }
        
        return ServiceResult.SUCCESS;
    }
	
	
	public void clearCart() {
		items.clear();
	}
	
	public double calculateTotal() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            total += (p.getPrice() * qty);
        }
        return total;
    }
	public Set<Product> getUniqueItems() {
        return items.keySet();
    }
	
	public List<Product> getItems() {
        List<Product> flatList = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                flatList.add(entry.getKey());
            }
        }
        return flatList;
    }
	
	
	public Receipt pay(PaymentMethod paymentMethod, Stock mainStock, Address billingAddress) {
        if (this.items.isEmpty()) {
            return null; 
        }

        // --- PHASE 1: VALIDATION ---
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product basketItem = entry.getKey();
            int totalQtyInBasket = entry.getValue();
            
            Product stockItem = mainStock.findProductById(basketItem.getProductID());
            
            if (stockItem == null || totalQtyInBasket > stockItem.getStock()) {
                return null; 
            }
        }

        // --- PHASE 2: PROCESSING ---
        double totalAmount = calculateTotal();
        
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            int totalQtyInBasket = entry.getValue();
            mainStock.updateStockValue(entry.getKey().getProductID(), -totalQtyInBasket);
        }
        
        Receipt finalReceipt = paymentMethod.processPayment(totalAmount, billingAddress);
        this.clearCart();
        
        return finalReceipt;
    }
	
}

