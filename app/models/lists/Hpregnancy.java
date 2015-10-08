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
public class Hpregnancy extends Model {
	public Hpregnancy() {
		
	}
	public Hpregnancy (String description) {
		this.description = description;
	}
	@Id
	Long id;
	String description;
	
	public static Finder<Long, Hpregnancy> find = new Finder<Long, Hpregnancy>(Long.class, Hpregnancy.class);
	
	public static Page<Hpregnancy> find(int page) {
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
