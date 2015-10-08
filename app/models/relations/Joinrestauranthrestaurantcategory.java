package models.relations;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.lists.Hrestaurantcategory;
import models.restaurant.Restaurant;

import com.avaje.ebean.Page;
import com.fasterxml.jackson.annotation.JsonBackReference;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class Joinrestauranthrestaurantcategory extends Model {
	public Joinrestauranthrestaurantcategory() {
		
	}
	public Joinrestauranthrestaurantcategory(Long restaurantid, Long cousineid) {
		super();
		this.restaurantid = restaurantid;
		this.hrestaurantcategoryid = cousineid;
	}
	@Id
	Long id;
	
	@ManyToOne
    @JoinColumn(name = "restaurantid", insertable=false, updatable=false)
	@JsonBackReference
	Restaurant restaurant;	
	
	@ManyToOne
    @JoinColumn(name = "hrestaurantcategoryid", insertable=false, updatable=false)
	@JsonBackReference
	Hrestaurantcategory category;	
	
	Long restaurantid; 
	Long hrestaurantcategoryid;
	
	public static Finder<Long, Joinrestauranthrestaurantcategory> find = new Finder<Long, Joinrestauranthrestaurantcategory>(Long.class, Joinrestauranthrestaurantcategory.class);
	
	public static Page<Joinrestauranthrestaurantcategory> find(int page) {
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
	public Long getHrestaurantcategoryid() {
		return hrestaurantcategoryid;
	}
	public void setHrestaurantcategoryid(Long hrestaurantcategoryid) {
		this.hrestaurantcategoryid = hrestaurantcategoryid;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public Hrestaurantcategory getCategory() {
		return category;
	}
	public void setCategory(Hrestaurantcategory category) {
		this.category = category;
	}
}
