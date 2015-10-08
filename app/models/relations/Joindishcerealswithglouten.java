package models.relations;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.lists.Hcerealswithglouten;
import models.restaurant.Dish;

import com.avaje.ebean.Page;
import com.fasterxml.jackson.annotation.JsonBackReference;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class Joindishcerealswithglouten extends Model {
	@Id
	Long id;
	
	@ManyToOne
    @JoinColumn(name = "dishid",  referencedColumnName="id")
	@JsonBackReference
	Dish dish;	
	
	@ManyToOne
    @JoinColumn(name = "hcerealswithgloutenid", referencedColumnName="id")
	@JsonBackReference
	Hcerealswithglouten cerealswithglouten;	
	
	public static Finder<Long, Joindishcerealswithglouten> find = new Finder<Long, Joindishcerealswithglouten>(Long.class, Joindishcerealswithglouten.class);
	
	public static Page<Joindishcerealswithglouten> find(int page) {
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

	public Hcerealswithglouten getCerealswithglouten() {
		return cerealswithglouten;
	}

	public void setCerealswithglouten(Hcerealswithglouten cerealswithglouten) {
		this.cerealswithglouten = cerealswithglouten;
	}

	public static Finder<Long, Joindishcerealswithglouten> getFind() {
		return find;
	}

	public static void setFind(Finder<Long, Joindishcerealswithglouten> find) {
		Joindishcerealswithglouten.find = find;
	}	
	
}
