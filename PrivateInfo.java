
public class PrivateInfo {
	
	private String firstname;
	private String lastname;
	private String username;
	private String password;

	public PrivateInfo(String firstname, String lastname, String username, String password) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
	}
	
	public String getPass() {
		return this.password;
	}
	
	public String getUser() {
		return this.username;
	}
	
	public String getName() {
		return this.firstname + " " + this.lastname;
	}
}
