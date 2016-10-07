package dit215;

public class Bar {
	
	private String name = "";
	private String address = "";
	private int postNumber = 0;
	private String city = "";	
	
	Bar(){
	}
	
	Bar(String name, String address, int postNumber, String city){
		this.name = name;
		this.address = address;
		this.postNumber = postNumber;
		this.city = city;
	}
	//Our get().
	public String getName(){
		return this.name;
	}
	public String getAddress(){
		return this.address;
	}
	public int getPostNumber(){
		return this.postNumber;
	}
	public String getCity(){
		return this.city;
	}
	//Our set().
	public void setName(String newName){
		this.name = newName;
	}
	public void setAddress(String newAddress){
		this.address = newAddress;
	}
	public void setPostNumber(int newPostNumber){
		this.postNumber = newPostNumber;
	}
	public void setCity(String newCity){
		this.city = newCity;
	}
	
}
