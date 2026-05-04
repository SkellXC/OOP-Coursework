
public class Receipt {
	private String receiptText;
	
	public Receipt(String receiptText) {
		this.receiptText = receiptText;
	}
	

	@Override
	public String toString() {
		return receiptText;
	}
	
}
