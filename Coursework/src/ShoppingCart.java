import  java.util.ArrayList;

public class ShoppingCart {
	private ArrayList<Product> items;
	
	public ShoppingCart() {
		this.items = new ArrayList<>();
	}
	

	public ServiceResult addItemToCart(Product product, int quantity) {
		if (quantity < 1) {
			return ServiceResult.INVALID_INPUT;
		}
		
		// Check if requested amount exceeds available stock
		int currentInCart = getProductCount(product.getProductID());
		if ((currentInCart + quantity) > product.getStock()) {
		    return ServiceResult.INSUFFICIENT_QUANTITY;
		}
		
	    for (int i = 0; i < quantity; i++) {
	        items.add(product);
	    }
	    return ServiceResult.SUCCESS;
	}

	
	public int getProductCount(int productID) {
	    int count = 0;
	    for (Product item : items) {
	        if (item.getProductID() == productID) {
	            count++;
	        }
	    }
	    return count;
	}

	public ServiceResult removeItemFromCart(Product product, int quantity) {
	    int currentCount = getProductCount(product.getProductID());
	    if (currentCount == 0) {
	    	// Checks if the item is in the cart
	    	return ServiceResult.NOT_FOUND;
	    }
	    if (quantity > currentCount) {
	    	// Checks if too many items are being removed
	        return ServiceResult.EXCEEDED_QUANTITY;
	    }
	    
	    for (int i = 0; i < quantity; i++) {
	        items.remove(product);
	    }
	    return ServiceResult.SUCCESS;
	}
	
	
	public void clearCart() {
		items.clear();
	}
	
	public double calculateTotal() {
		double total = 0;
		for(Product item : items) {
			total += item.getPrice();
		}
		return total;
	}

	
	public ArrayList<Product> getItems() {
	    return items;
	}
	
	
	public Receipt pay(PaymentMethod paymentMethod, Stock mainStock, Address billingAddress) {
	    if (this.items.isEmpty()) {
	        return null; 
	    }

	    ArrayList<Integer> processedIDs = new ArrayList<>();
	    
	    // --- PHASE 1: VALIDATION (The Final Stock Check) ---
	    for (Product basketItem : this.items) {
	        int id = basketItem.getProductID();
	        
	        if (!processedIDs.contains(id)) {
	            int totalQtyInBasket = getProductCount(id);
	            Product stockItem = mainStock.findProductById(id);
	            
	            // FINAL CHECK: Does the store still have enough right now?
	            if (stockItem == null || totalQtyInBasket > stockItem.getStock()) {
	                return null; // Abort the entire checkout!
	            }
	            processedIDs.add(id);
	        }
	    }

	    // --- PHASE 2: PROCESSING (Only runs if ALL items passed Phase 1) ---
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

