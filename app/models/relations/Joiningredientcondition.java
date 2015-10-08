package models.relations;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import models.ingredient.Ingredient;
import models.lists.Hcondition;
import models.lists.Hingredientcategory;
import models.user.Profile;

import com.fasterxml.jackson.annotation.JsonBackReference;

import play.db.ebean.Model;

@Entity
public class Joiningredientcondition extends Model {
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="ingredientid", referencedColumnName="id")	
	@JsonBackReference
	Ingredient ingredient; 
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="hconditionid", referencedColumnName="id")	
	@JsonBackReference
	Hcondition condition;
	
	
	int ingredientid;
	int hconditionid;
	
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

	public Hcondition getCondition() {
		return condition;
	}

	public void setCondition(Hcondition condition) {
		this.condition = condition;
	}

	public int getIngredientid() {
		return ingredientid;
	}

	public void setIngredientid(int ingredientid) {
		this.ingredientid = ingredientid;
	}

	public int getHconditionid() {
		return hconditionid;
	}

	public void setHconditionid(int hconditionid) {
		this.hconditionid = hconditionid;
	}
	
}
