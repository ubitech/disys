package models.relations;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.lists.Hcousine;
import models.restaurant.Restaurant;

import com.avaje.ebean.Page;
import com.fasterxml.jackson.annotation.JsonBackReference;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class Joinrestaurantcousine extends Model {
	public Joinrestaurantcousine() {
		
	}
	public Joinrestaurantcousine(Long restaurantid, Long cousineid) {
		super();
		this.restaurantid = restaurantid;
		this.cousineid = cousineid;
	}
	@Id
	Long id;
	 
	@ManyToOne
    @JoinColumn(name = "restaurantid", insertable=false, updatable=false)
	@JsonBackReference
	Restaurant restaurant;
	
	@ManyToOne
    @JoinColumn(name = "cousineid", insertable=false, updatable=false)
	@JsonBackReference
	Hcousine cousine;
	
	Long restaurantid;
	Long cousineid;
	
	public static Finder<Long, Joinrestaurantcousine> find = new Finder<Long, Joinrestaurantcousine>(Long.class, Joinrestaurantcousine.class);
	
	public static Page<Joinrestaurantcousine> find(int page) {
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
	public Long getRestaurantid() {
		return restaurantid;
	}
	public void setRestaurantid(Long restaurantid) {
		this.restaurantid = restaurantid;
	}
	public Long getCousineid() {
		return cousineid;
	}
	public void setCousineid(Long cousineid) {
		this.cousineid = cousineid;
	}


}
