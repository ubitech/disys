package models.lists;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Page;

import play.db.ebean.Model;


@SuppressWarnings("serial")
@Entity
public class Hfruitwithshell extends Model {
	public Hfruitwithshell() {
		
	}
	public Hfruitwithshell (String description) {
		this.description = description;
	}
	@Id
	Long id;
	String description;
	
	public static Finder<Long, Hfruitwithshell> find = new Finder<Long, Hfruitwithshell>(Long.class, Hfruitwithshell.class);
	
	public static Page<Hfruitwithshell> find(int page) {
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
