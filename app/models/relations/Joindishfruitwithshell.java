package models.relations;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.lists.Hfruitwithshell;
import models.restaurant.Dish;

import com.avaje.ebean.Page;
import com.fasterxml.jackson.annotation.JsonBackReference;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class Joindishfruitwithshell extends Model {
	@Id
	Long id;
	
	@ManyToOne
    @JoinColumn(name = "dishid", referencedColumnName="id")
	@JsonBackReference
	Dish dish;	
	
	@ManyToOne
    @JoinColumn(name = "hfruitwithshellid", referencedColumnName="id")
	@JsonBackReference
	Hfruitwithshell fruitswithshell;	
	
	public static Finder<Long, Joindishfruitwithshell> find = new Finder<Long, Joindishfruitwithshell>(Long.class, Joindishfruitwithshell.class);
	
	public static Page<Joindishfruitwithshell> find(int page) {
	    return 
	            find.where()
	                .orderBy("id asc")
	                .findPagingList(10)
	                .setFetchAhead(false)
	                .getPage(page);
	}

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

	public Hfruitwithshell getFruitswithshell() {
		return fruitswithshell;
	}

	public void setFruitswithshell(Hfruitwithshell fruitswithshell) {
		this.fruitswithshell = fruitswithshell;
	}

	public static Finder<Long, Joindishfruitwithshell> getFind() {
		return find;
	}

	public static void setFind(Finder<Long, Joindishfruitwithshell> find) {
		Joindishfruitwithshell.find = find;
	}	
}
