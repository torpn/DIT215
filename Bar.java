package dit215;

public class Bar {
	//Joacim Eberlen
	public static int barNumber;
	private String name = "";
	private String address = "";
	private String postalCode = "";
	private String city = "";	
	//Justinas Stirbys
	private int id = 0;
	private String description = "";
	private String openingHours = "";
	private String closingHours = "";
	
	Bar(){
	}
	
	//Justinas Stirbys
	Bar(String name, int id){
		this.name = name;
		this.id = id;
	}
	
	//Justinas Stirbys
	Bar(String name, String address, String city, int id){
		this.name = name;
		this.address = address;
		this.city = city;
		this.id = id;
	}
	
	//Justinas Stirbys
	Bar(String name, String address, String postalCode, String city, int id){
		this.name = name;
		this.address = address;
		this.postalCode = postalCode;
		this.city = city;
		this.id = id;
	}
	
	//Justinas Stirbys
	Bar(String name, String address, String city, int id, String openingHours){
		this.name = name;
		this.address = address;
		this.city = city;
		this.id = id;
		this.openingHours = openingHours;
	}
	
	//Justinas Stirbys
	Bar(String name, String address, String city, int id, String openingHours, String closingHours){
		this.name = name;
		this.address = address;
		this.city = city;
		this.id = id;
		this.openingHours = openingHours;
		this.closingHours = closingHours;
	}
	
	//Joacim Eberlen
	Bar(String name, String address, String city, int id, String postalCode, String description, String openingHours, String closingHours){
		this.name = name;
		this.address = address;
		this.city = city;
		this.id = id;
		this.postalCode = postalCode;
		this.description = description;
		this.openingHours = openingHours;
		this.closingHours = closingHours;
	}
			
	//Our get().
	//Joacim Eberlen
	public String getName(){
		return this.name;
	}
	public String getAddress(){
		return this.address;
	}
	public String getPostalCode(){
		return this.postalCode;
	}
	public String getCity(){
		return this.city;
	}
	//Justinas Stirbys
	public int getId(){
		return this.id;
	}
	public String getDescription(){
		return this.description;
	}
	public String getOpeningHours(){
		return this.openingHours;
	}
	public String getClosingHours(){
		return this.closingHours;
	}
	//Our set().
	//Joacim Eberlen
	public void setName(String newName){
		this.name = newName;
	}
	public void setAddress(String newAddress){
		this.address = newAddress;
	}
	public void setPostalCode(String newPostalCode){
		this.postalCode = newPostalCode;
	}
	public void setCity(String newCity){
		this.city = newCity;
	}
	//Justinas Stirbys
	public void setId(int newId){
		this.id = newId;
	}
	public void setDescription(String newDescription){
		this.description = newDescription;
	}
	public void setOpeningHours(String newOpeningHours){
		this.openingHours = newOpeningHours;
	}
	public void setClosingHours(String newClosingHours){
		this.closingHours = newClosingHours;
	}
}