package models.lists;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import models.relations.Joinrestaurantcousine;
import models.relations.Joinrestauranthrestaurantcategory;

import com.avaje.ebean.Page;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class Hrestaurantcategory extends Model {
	public Hrestaurantcategory() {
		
	}
	public Hrestaurantcategory (String description) {
		this.description = description;
	}
	@Id
	Long id;
	String description;
	
	@OneToMany(mappedBy = "category")
	private Set<Joinrestauranthrestaurantcategory> restaurantcategories = new HashSet<Joinrestauranthrestaurantcategory>();	
	
	public static Finder<Long, Hrestaurantcategory> find = new Finder<Long, Hrestaurantcategory>(Long.class, Hrestaurantcategory.class);
	
	public static Page<Hrestaurantcategory> find(int page) {
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<Joinrestauranthrestaurantcategory> getRestaurantcategories() {
		return restaurantcategories;
	}
	public void setRestaurantcategories(
			Set<Joinrestauranthrestaurantcategory> restaurantcategories) {
		this.restaurantcategories = restaurantcategories;
	}
}
