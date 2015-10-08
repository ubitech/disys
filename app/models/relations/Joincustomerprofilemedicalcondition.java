package models.relations;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.avaje.ebean.Page;
import com.fasterxml.jackson.annotation.JsonBackReference;

import models.lists.Medicalcondition;
import models.user.Profile;
import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
@Table(name = "joincustomerprofilemedicalcondition")
public class Joincustomerprofilemedicalcondition extends Model {
	@Id
	Long id;
	
	@ManyToOne
	@JoinColumn(name="customerprofileid", insertable=false, updatable=false)	
	@JsonBackReference
	Profile profile;	
	
	@ManyToOne
	@JoinColumn(name="medicalconditionid", insertable=false, updatable=false)
	@JsonBackReference
	Medicalcondition medicalcondition;
	
	Long customerprofileid;
	Long medicalconditionid;
	public static Finder<Long, Joincustomerprofilemedicalcondition> find = new Finder<Long, Joincustomerprofilemedicalcondition>(Long.class, Joincustomerprofilemedicalcondition.class);
	
	public static Page<Joincustomerprofilemedicalcondition> find(int page) {
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

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Medicalcondition getMedicalcondition() {
		return medicalcondition;
	}

	public void setMedicalcondition(Medicalcondition medicalcondition) {
		this.medicalcondition = medicalcondition;
	}

	public Joincustomerprofilemedicalcondition(Long id, Profile profile,
			Medicalcondition medicalcondition) {
		super();
		this.id = id;
		this.profile = profile;
		this.medicalcondition = medicalcondition;
	}

	public Joincustomerprofilemedicalcondition() {

	}
	public Long getCustomerprofileid() {
		return customerprofileid;
	}
	public void setCustomerprofileid(Long customerprofileid) {
		this.customerprofileid = customerprofileid;
	}
	public Long getMedicalconditionid() {
		return medicalconditionid;
	}
	public void setMedicalconditionid(Long medicalconditionid) {
		this.medicalconditionid = medicalconditionid;
	}		
}
