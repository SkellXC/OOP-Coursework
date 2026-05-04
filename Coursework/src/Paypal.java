import java.time.LocalDate;

public class Paypal implements PaymentMethod {
	private String email;
	
	public Paypal(String email) {
		this.email = email;
	}
	
	@Override
	public Receipt processPayment(double total, Address address) {
		String today = LocalDate.now().toString();

		String receiptText = String.format("%s"
				+ "\nAmount £%.2f has been paid via Paypal"
				+ "\nEmail used: %s"
				+ "\nBilling Address: %s",
				 today, total, email, address.getFullAddress());
		return new Receipt(receiptText);
	}
}

