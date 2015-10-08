package models.relations;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.restaurant.Dish;
import models.user.User;
import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class Joinuserdish extends Model {
	@Id
	Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="userid", referencedColumnName="id")	
	User user;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="dishid", referencedColumnName="id")		
	Dish dish;
	
	Date dateofselection;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDateofselection() {
		return dateofselection;
	}

	public void setDateofselection(Date dateofselection) {
		this.dateofselection = dateofselection;
	}

}
