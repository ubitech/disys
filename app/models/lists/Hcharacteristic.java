package models.lists;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import models.relations.Joindishcharacteristic;
import com.avaje.ebean.Page;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class Hcharacteristic extends Model {
	public Hcharacteristic() {
		
	}
	public Hcharacteristic (String description) {
		this.description = description;
	}
	@Id
	Long id;
	String description;
	
	@OneToMany(mappedBy = "characteristic") 
	@JsonManagedReference
    private List<Joindishcharacteristic> dishcharacteristic;		
	
	public static Finder<Long, Hcharacteristic> find = new Finder<Long, Hcharacteristic>(Long.class, Hcharacteristic.class);
	
	public static Page<Hcharacteristic> find(int page) {
	    return 
	            find.where()
	                .orderBy("id desc")
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
	public List<Joindishcharacteristic> getDishcharacteristic() {
		return dishcharacteristic;
	}
	public void setDishcharacteristic(
			List<Joindishcharacteristic> dishcharacteristic) {
		this.dishcharacteristic = dishcharacteristic;
	}
}