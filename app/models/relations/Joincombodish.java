package models.relations;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.restaurant.Dish;
import play.db.ebean.Model;

@Entity
public class Joincombodish extends Model {
	@Id
	Long id;
	Integer amount;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="comboid", referencedColumnName="id")	
	Dish combo;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="dishid", referencedColumnName="id")		
	Dish dish;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Dish getCombo() {
		return combo;
	}
	public void setCombo(Dish combo) {
		this.combo = combo;
	}
	public Dish getDish() {
		return dish;
	}
	public void setDish(Dish dish) {
		this.dish = dish;
	}		
}
