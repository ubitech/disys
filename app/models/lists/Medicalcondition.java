package models.lists;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.avaje.ebean.Page;

import models.relations.Joincustomerprofilemedicalcondition;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;


@SuppressWarnings("serial")
@Entity
public class Medicalcondition extends Model{
	public Medicalcondition() {
		
	}
	public Medicalcondition (String description) {
		this.description = description;
	}

	@Id
	Long id;
	String description;
	
	@OneToMany(mappedBy = "medicalcondition")
	private Set<Joincustomerprofilemedicalcondition> profileConditions = new HashSet<Joincustomerprofilemedicalcondition>();
	
	public static Finder<Long, Medicalcondition> find = new Finder<Long, Medicalcondition>(Long.class, Medicalcondition.class);
	
	public static Page<Medicalcondition> find(int page) {
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
	public Set<Joincustomerprofilemedicalcondition> getProfileConditions() {
		return profileConditions;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setProfileConditions(
			Set<Joincustomerprofilemedicalcondition> profileConditions) {
		this.profileConditions = profileConditions;
	}	
}
