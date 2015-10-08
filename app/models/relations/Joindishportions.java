package models.relations;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Joindishportions extends Model {
	@Id
	Long id;
	
	Long dishid;
	Long portionid;
	Double price;
	
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDishid() {
		return dishid;
	}
	public void setDishid(Long dishid) {
		this.dishid = dishid;
	}
	public Long getPortionid() {
		return portionid;
	}
	public void setPortionid(Long portionid) {
		this.portionid = portionid;
	}
}
