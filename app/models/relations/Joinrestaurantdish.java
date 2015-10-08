package models.relations;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.restaurant.Dish;
import models.restaurant.Restaurant;
import play.db.ebean.Model;

@Entity
public class Joinrestaurantdish extends Model {
	@Id
	Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="restaurantid", referencedColumnName="id")	
	Restaurant restaurant;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="dishid", referencedColumnName="id")		
	Dish dish;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

}
