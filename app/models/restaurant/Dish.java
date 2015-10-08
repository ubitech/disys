package models.restaurant;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import models.ingredient.Ingredient;
import models.lists.Hcategory;
import models.lists.Hcharacteristic;
import models.lists.Hcousine;
import models.lists.Hingredientcategory;
import models.relations.Joindishcharacteristic;
import models.relations.Joindishingredient;
import models.relations.Joiningredientcondition;
import models.relations.Joiningredientingredientcategory;
import models.relations.Joinrestaurantcousine;
import play.Logger;
import play.db.ebean.Model;

@Entity
public class Dish extends Model implements Comparable<Dish> {
	@Id
	Long id;
	String name; 
	Double portions;
	Double price;
	Double calories;
	Integer iscombo;
	Date dateadded;
	Long dayperiod;
	Integer combocategoryid;
	String imagepath;
	@Lob
	public byte[] picture;
	
	@Transient
	Boolean selectable = true;
	
	@Transient
	ArrayList<String> listOfIngredientsArray = new ArrayList<String>();
	@Transient
	ArrayList<String> listOfIngredientConditionsArray = new ArrayList<String>();
	
	@Transient
	Boolean exclude = false;
	
	@Transient
	Double priority = 0.0;
	
	@ManyToOne
	@JoinColumn(name="cousineid", referencedColumnName="id")	
	Hcousine cousine;
	
	
	@ManyToOne
	@JoinColumn(name="categoryid", referencedColumnName="id")	
	Hcategory category;	
	
	@OneToMany(mappedBy = "dish") 
	@JsonManagedReference
    private List<Joindishingredient> dishingredients;
	
	@OneToMany(mappedBy = "dish") 
	@JsonManagedReference
    private List<Joindishcharacteristic> dishcharacteristics;	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPortions() {
		return portions;
	}

	public void setPortions(Double portions) {
		this.portions = portions;
	}

	public Hcousine getCousine() {
		return cousine;
	}

	public void setCousine(Hcousine cousine) {
		this.cousine = cousine;
	}

	public Hcategory getCategory() {
		return category;
	}

	public void setCategory(Hcategory category) {
		this.category = category;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getCalories() {
		return calories;
	}

	public void setCalories(Double calories) {
		this.calories = calories;
	}

	public List<Joindishingredient> getDishingredients() {		
		return dishingredients;
	}

	public void setDishingredients(List<Joindishingredient> dishingredients) {
		this.dishingredients = dishingredients;
	}
	
	public String[] getListOfIngredientCategoriesArrayStr(){
		if (listOfIngredientsArray.size() == 0){
			for (Joindishingredient dishingredient : this.dishingredients){
				
				List<Joiningredientingredientcategory> ingredientingredientcategory = dishingredient.getIngredient().getIngredientingredientcategories();
				
				for (Joiningredientingredientcategory ingredientcategory : ingredientingredientcategory){
					listOfIngredientsArray.add(Integer.toString(ingredientcategory.getHingredientcategoryid()));
				}				
			}
    		return listOfIngredientsArray.toArray(new String[listOfIngredientsArray.size()]);
		}
		else{
			return listOfIngredientsArray.toArray(new String[listOfIngredientsArray.size()]);
		}
		
	}
	
	public String[] getListOfIngredientConditionsArrayStr(){
		if (listOfIngredientConditionsArray.size() == 0){
			for (Joindishingredient dishingredient : this.dishingredients){
				
				List<Joiningredientcondition> ingredientconditions = dishingredient.getIngredient().getIngredientconditions();
				
				for (Joiningredientcondition ingredientcondition : ingredientconditions){
					listOfIngredientConditionsArray.add(Integer.toString(ingredientcondition.getHconditionid()));
				}				
			}
    		return listOfIngredientConditionsArray.toArray(new String[listOfIngredientConditionsArray.size()]);
		}
		else{
			return listOfIngredientConditionsArray.toArray(new String[listOfIngredientConditionsArray.size()]);
		}
		
	}

	public Date getDateadded() {
		return dateadded;
	}

	public void setDateadded(Date dateadded) {
		this.dateadded = dateadded;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public Long getDayperiod() {
		return dayperiod;
	}

	public Boolean getSelectable() {
		return selectable;
	}

	public void setSelectable(Boolean selectable) {
		this.selectable = selectable;
	}

	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	public Integer getCombocategoryid() {
		return combocategoryid;
	}

	public void setCombocategoryid(Integer combocategoryid) {
		this.combocategoryid = combocategoryid;
	}

	public void setDayperiod(Long l) {
		this.dayperiod = l;
	}

	public Boolean getExclude() {
		return exclude;
	}

	public Integer getIscombo() {
		return iscombo;
	}

	public void setIscombo(Integer iscombo) {
		this.iscombo = iscombo;
	}

	public void setExclude(Boolean exclude) {
		this.exclude = exclude;
	}

	public Double getPriority() {
		return priority;
	}

	public void setPriority(Double priority) {
		this.priority = priority;
	}

	public ArrayList<String> getListOfIngredientsArray() {
		return listOfIngredientsArray;
	}

	public void setListOfIngredientsArray(ArrayList<String> listOfIngredientsArray) {
		this.listOfIngredientsArray = listOfIngredientsArray;
	}

	public ArrayList<String> getListOfIngredientConditionsArray() {
		return listOfIngredientConditionsArray;
	}

	public void setListOfIngredientConditionsArray(
			ArrayList<String> listOfIngredientConditionsArray) {
		this.listOfIngredientConditionsArray = listOfIngredientConditionsArray;
	}

	public List<Joindishcharacteristic> getDishcharacteristics() {
		return dishcharacteristics;
	}

	public void setDishcharacteristics(
			List<Joindishcharacteristic> dishcharacteristics) {
		this.dishcharacteristics = dishcharacteristics;
	}

	@Override
	public int compareTo(Dish other) {
		if (this.priority > other.getPriority()) {
			return -1;
		} else if (this.priority < other.getPriority()) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public int hashCode() {
		return (int) (long) (this.id);
	}	
}
