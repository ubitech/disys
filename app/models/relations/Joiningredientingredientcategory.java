package models.relations;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import models.ingredient.Ingredient;
import models.lists.Hingredientcategory;
import models.user.Profile;

import com.fasterxml.jackson.annotation.JsonBackReference;

import play.db.ebean.Model;

@Entity
public class Joiningredientingredientcategory extends Model {
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="ingredientid", referencedColumnName="id")		
	@JsonBackReference
	Ingredient ingredient; 
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="hingredientcategoryid", referencedColumnName="id")
	@JsonBackReference
	Hingredientcategory ingredientcategory;
                   	
	int ingredientid;
	int hingredientcategoryid;	
	
	@Id
	Long id;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public Hingredientcategory getIngredientcategory() {
		return ingredientcategory;
	}

	public void setIngredientcategory(Hingredientcategory ingredientcategory) {
		this.ingredientcategory = ingredientcategory;
	}

	public int getIngredientid() {
		return ingredientid;
	}

	public void setIngredientid(int ingredientid) {
		this.ingredientid = ingredientid;
	}

	public int getHingredientcategoryid() {
		return hingredientcategoryid;
	}

	public void setHingredientcategoryid(int hingredientcategoryid) {
		this.hingredientcategoryid = hingredientcategoryid;
	}
	
}
