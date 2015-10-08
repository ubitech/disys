package models.relations;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.lists.Hallergen;
import models.restaurant.Dish;
import play.db.ebean.Model;

@Entity
public class Joindishallergen extends Model {
	@Id
	Long id;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="allergenid", referencedColumnName="id")		
	Hallergen allergen;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="dishid", referencedColumnName="id")		
	Dish dish;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Hallergen getAllergen() {
		return allergen;
	}
	public void setAllergen(Hallergen allergen) {
		this.allergen = allergen;
	}
	public Dish getDish() {
		return dish;
	}
	public void setDish(Dish dish) {
		this.dish = dish;
	}
	
}
