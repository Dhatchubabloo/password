package javaDatabaseDemo;

public class CustomerInfo{
	private String name;
	private String city;
	private int customerId;
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name){
		this.name  = name;
	}
	public void setCity(String city){
		this.city = city;
	}
	public void setCustomerId(int customerId){
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public String getCity() {
		return city;
	}
	public int getCustomerId(){
		return customerId;
	}
	public String toString(){
		return "------------------------------------------------------"+
				"\n"+"Customer Id :"+customerId+" Name :"+name+" City :"+city+
				"\n"+"------------------------------------------------------";
	}
}
