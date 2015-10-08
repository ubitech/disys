package models.relations;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.lists.Hcharacteristic;
import models.restaurant.Dish;

import com.fasterxml.jackson.annotation.JsonBackReference;

import play.db.ebean.Model;

@Entity
public class Joindishcharacteristic extends Model {
	@Id
	Long id;	

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="dishid", referencedColumnName="id")	
	@JsonBackReference
	Dish dish;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="characteristicid", referencedColumnName="id")	
	@JsonBackReference
	Hcharacteristic characteristic;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Dish getDish() {
		return dish;
	}
	public void setDish(Dish dish) {
		this.dish = dish;
	}
	public Hcharacteristic getCharacteristic() {
		return characteristic;
	}
	public void setCharacteristic(Hcharacteristic characteristic) {
		this.characteristic = characteristic;
	}	
}
