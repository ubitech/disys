package models.relations;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import models.ingredient.Ingredient;
import models.restaurant.Dish;
import play.db.ebean.Model;

@Entity
public class Joindishingredient extends Model {
	@Id
	Long id;
	Double amount;
	int metric;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="dishid", referencedColumnName="id")	
	@JsonBackReference
	Dish dish;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="ingredientid", referencedColumnName="id")	
	@JsonBackReference
	Ingredient ingredient;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public int getMetric() {
		return metric;
	}
	public void setMetric(int metric) {
		this.metric = metric;
	}
	public Dish getDish() {
		return dish;
	}
	public void setDish(Dish dish) {
		this.dish = dish;
	}
	public Ingredient getIngredient() {
		return ingredient;
	}
	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}
	
	
}
