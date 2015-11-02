package beans;

import java.util.ArrayList;

public class DishDetails {
	public boolean selectable = true;
	String name;
	String restaurantname;
	String location;
	double lat;
	double lng;
	Long dishid;
	double price;
	String characteristic;
	Integer calories;
	ArrayList<String> preferenceExplanation = null;

	public String getCharacteristic() {
		return characteristic;
	}
	public void setCharacteristic(String characteristic) {
		this.characteristic = characteristic;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Long getDishid() {
		return dishid;
	}
	public void setDishid(Long dishid) {
		this.dishid = dishid;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRestaurantname() {
		return restaurantname;
	}
	public void setRestaurantname(String restaurantname) {
		this.restaurantname = restaurantname;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getCalories() {
		return calories;
	}
	public void setCalories(Integer calories) {
		this.calories = calories;
	}	
	public ArrayList<String> getPreferenceExplanation() {
		return preferenceExplanation;
	}
	public void setPreferenceExplanation(ArrayList<String> preferenceExplanation) {
		this.preferenceExplanation = preferenceExplanation;
	}
	
	public void addPreferenceExplanation(String preferenceExplanation) {
		if (this.preferenceExplanation == null){
			this.preferenceExplanation = new ArrayList<String>();
		}
		this.preferenceExplanation.add(preferenceExplanation);
	}
	
	
}
