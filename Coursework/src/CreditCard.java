import java.time.LocalDate;

	public class CreditCard implements PaymentMethod{
		private String cardNumber;
		private int securityCode;
		
		public CreditCard(String cardNumber, int securityCode) {
			this.cardNumber = cardNumber;
			this.securityCode = securityCode;
			
		}
		
		@Override
		public Receipt processPayment(double total, Address address) {
			String today = LocalDate.now().toString();
			
			String lastDigits = "";

			if (cardNumber.length() >= 4) {
				lastDigits = cardNumber.substring(cardNumber.length() - 4);
			} else {
				lastDigits = cardNumber;
			}


			String receiptText = String.format("%s"
					+ "\nAmount £%.2f has been paid via Credit Card"
					+ "\nCard Number: ** %s"// Easily adjustable for real card details
					+ "\nBilling Address: %s",
					 today, total, lastDigits, address.getFullAddress());
			return new Receipt(receiptText);
		}
	}

