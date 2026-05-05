
public class Paypal implements PaymentMethod {
	private String email;
	
	public Paypal(String email) {
		this.email = email;
	}
	
	@Override
	public Receipt processPayment(double total, Address address) {
		String today = java.time.LocalDate.now().toString();

		String receiptText = String.format("%.2f paid via PayPal using %s on %s . Billing address: %s",
				total, email, today, address.getFullAddress());
		return new Receipt(receiptText);
	}
}

