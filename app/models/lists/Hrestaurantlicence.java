package models.lists;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Page;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class Hrestaurantlicence extends Model {
	public Hrestaurantlicence() {
		
	}
	public Hrestaurantlicence (String description) {
		this.description = description;
	}
	@Id
	Long id;
	String description;
	
	public static Finder<Long, Hrestaurantlicence> find = new Finder<Long, Hrestaurantlicence>(Long.class, Hrestaurantlicence.class);
	
	public static Page<Hrestaurantlicence> find(int page) {
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
}
