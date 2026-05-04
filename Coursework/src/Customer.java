
public class Customer extends User{
	private ShoppingCart basket;
	
	public Customer(int userID, String name, Address address){
		super(userID, name, address);
		
		this.basket = new ShoppingCart();
		
	}
	public ShoppingCart getBasket() {
		return basket;
	}
	
	public ServiceResult addToBasket(Product product, int quantity) {
	        return this.basket.addItemToCart(product, quantity);
	}
	
	public ServiceResult removeItem(Product product, int quantity) {
		return this.basket.removeItemFromCart(product, quantity);
	}
	
	
}
