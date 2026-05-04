public abstract class User{
	private int userID;
	private String name;
	private Address address;
	
	public User(int userID, String name, Address address) {
		this.userID = userID;
		this.name = name;
		this.address = address;
	}
	public int getUserID() {
		return userID;
	}
	public String getName() { 
		return name;
	}
	
	public Address getAddress() {
		return address;
	}
}