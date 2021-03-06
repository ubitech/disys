package models.lists;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import models.relations.Joinrestaurantcousine;

import com.avaje.ebean.Page;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class Hcousine extends Model {
	public Hcousine() {
		
	}
	public Hcousine (String description) {
		this.description = description;
	}
	@Id
	Long id;
	String description;
	
	@OneToMany(mappedBy = "cousine")
	private Set<Joinrestaurantcousine> restaurantcousines = new HashSet<Joinrestaurantcousine>();
	
	public static Finder<Long, Hcousine> find = new Finder<Long, Hcousine>(Long.class, Hcousine.class);
	
	public static Page<Hcousine> find(int page) {
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
	public Set<Joinrestaurantcousine> getRestaurantcousines() {
		return restaurantcousines;
	}
	public void setRestaurantcousines(Set<Joinrestaurantcousine> restaurantcousines) {
		this.restaurantcousines = restaurantcousines;
	}
}
