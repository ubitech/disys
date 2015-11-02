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
	
	@Transient
	Double breadPriority = 0.0;
	
	@Transient
	Double eggsPriority = 0.0;
	
	@Transient
	Double fetaPriority = 0.0;
	
	@Transient
	Double fishPriority = 0.0;
	
	@Transient
	Double kaseriPriority = 0.0;
	
	@Transient
	Double milkPriority = 0.0;
	
	@Transient
	Double lactosePriority = 0.0;
	
	@Transient
	Double lampPriority = 0.0;
	
	@Transient
	Double porkPriority = 0.0;
	
	@Transient
	Double seedsPriority = 0.0;
	
	@Transient
	Double shellsPriority = 0.0;
	
	@Transient
	Double sogiaPriority = 0.0;
	
	@Transient
	Double vealPriority = 0.0;
	
	@Transient
	Double wheatPriority = 0.0;
	
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
	
	public Double getBreadPriority() {
		return breadPriority;
	}

	public void setBreadPriority(Double breadPriority) {
		this.breadPriority = breadPriority;
	}

	public Double getEggsPriority() {
		return eggsPriority;
	}

	public void setEggsPriority(Double eggsPriority) {
		this.eggsPriority = eggsPriority;
	}

	public Double getFetaPriority() {
		return fetaPriority;
	}

	public void setFetaPriority(Double fetaPriority) {
		this.fetaPriority = fetaPriority;
	}

	public Double getFishPriority() {
		return fishPriority;
	}

	public void setFishPriority(Double fishPriority) {
		this.fishPriority = fishPriority;
	}

	public Double getKaseriPriority() {
		return kaseriPriority;
	}

	public void setKaseriPriority(Double kaseriPriority) {
		this.kaseriPriority = kaseriPriority;
	}

	public Double getMilkPriority() {
		return milkPriority;
	}

	public void setMilkPriority(Double milkPriority) {
		this.milkPriority = milkPriority;
	}

	public Double getLactosePriority() {
		return lactosePriority;
	}

	public void setLactosePriority(Double lactosePriority) {
		this.lactosePriority = lactosePriority;
	}

	public Double getLampPriority() {
		return lampPriority;
	}

	public void setLampPriority(Double lampPriority) {
		this.lampPriority = lampPriority;
	}

	public Double getPorkPriority() {
		return porkPriority;
	}

	public void setPorkPriority(Double porkPriority) {
		this.porkPriority = porkPriority;
	}

	public Double getSeedsPriority() {
		return seedsPriority;
	}

	public void setSeedsPriority(Double seedsPriority) {
		this.seedsPriority = seedsPriority;
	}

	public Double getShellsPriority() {
		return shellsPriority;
	}

	public void setShellsPriority(Double shellsPriority) {
		this.shellsPriority = shellsPriority;
	}

	public Double getSogiaPriority() {
		return sogiaPriority;
	}

	public void setSogiaPriority(Double sogiaPriority) {
		this.sogiaPriority = sogiaPriority;
	}

	public Double getVealPriority() {
		return vealPriority;
	}

	public void setVealPriority(Double vealPriority) {
		this.vealPriority = vealPriority;
	}

	public Double getWheatPriority() {
		return wheatPriority;
	}

	public void setWheatPriority(Double wheatPriority) {
		this.wheatPriority = wheatPriority;
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
	
	public double getIngredientPreferences(){
		
		double result = breadPriority +
				eggsPriority + 
				fetaPriority +
				fishPriority +
				kaseriPriority +
				milkPriority +
				lactosePriority +
				lampPriority +
				porkPriority +
				seedsPriority +
				shellsPriority +
				sogiaPriority +
				vealPriority +
				wheatPriority;
		return result;
	}
}
