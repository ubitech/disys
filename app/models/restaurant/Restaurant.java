package models.restaurant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import models.lists.Hcategory;
import models.lists.Hcousine;
import models.lists.Hpricerange;
import models.lists.Hrestaurantlicence;
import models.relations.Joinrestaurantcousine;
import models.relations.Joinrestauranthrestaurantcategory;
import models.user.User;

import com.avaje.ebean.Page;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mysql.jdbc.Blob;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class Restaurant extends Model {
	
	public static Finder<Long, Restaurant> find = new Finder<Long, Restaurant>(Long.class, Restaurant.class);
	
	public static Page<Restaurant> find(int page) {
	    return 
	            find.where()
	                .orderBy("id asc")
	                .findPagingList(10)
	                .setFetchAhead(false)
	                .getPage(page);
	}
	
	@Id
	Long id;
	String name;
	String organisationname;
	String city;
	Integer postcode;
	String street;
	String streetnumber;
	String personinchargesurname;
	String personinchargename;
	String phonenumber;
	String mobilephonenumber;
	String fax;
	String email;
	String webpage;
	Double latitude;
	Double longtitude;
	String extrainfo;
	String imagepath;
	@Lob
	public byte[] picture;
	
	Long userid;
	
	ArrayList<Integer> joinrestaurantcousine;
	ArrayList<Integer> joinrestauranthrestaurantcategory;
	
    @OneToMany(mappedBy = "restaurant")
    @JsonManagedReference
    private Set<Joinrestaurantcousine> restaurantcousines = new HashSet<Joinrestaurantcousine>();
    
    @OneToMany(mappedBy = "restaurant")
    @JsonManagedReference
    private Set<Joinrestauranthrestaurantcategory> restaurantcategories = new HashSet<Joinrestauranthrestaurantcategory>();
    
	@ManyToOne
	@JoinColumn(name="hpricerangeid", referencedColumnName="id")	
	Hpricerange pricerange;    
	
	@ManyToOne
	@JoinColumn(name="hrestaurantlicenceid", referencedColumnName="id")	
	Hrestaurantlicence restaurantlicence;  	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Integer getPostcode() {
		return postcode;
	}
	public void setPostcode(Integer postcode) {
		this.postcode = postcode;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStreetnumber() {
		return streetnumber;
	}
	public void setStreetnumber(String streetnumber) {
		this.streetnumber = streetnumber;
	}
	public String getPersoninchargesurname() {
		return personinchargesurname;
	}
	public void setPersoninchargesurname(String personinchargesurname) {
		this.personinchargesurname = personinchargesurname;
	}
	public String getPersoninchargename() {
		return personinchargename;
	}
	public void setPersoninchargename(String personinchargename) {
		this.personinchargename = personinchargename;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public String getImagepath() {
		return imagepath;
	}
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getMobilephonenumber() {
		return mobilephonenumber;
	}
	public String getExtrainfo() {
		return extrainfo;
	}
	public void setExtrainfo(String extrainfo) {
		this.extrainfo = extrainfo;
	}
	public void setMobilephonenumber(String mobilephonenumber) {
		this.mobilephonenumber = mobilephonenumber;
	}
	public byte[] getPicture() {
		return picture;
	}
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<Joinrestaurantcousine> getRestaurantcousines() {
		return restaurantcousines;
	}
	public void setRestaurantcousines(Set<Joinrestaurantcousine> restaurantcousines) {
		this.restaurantcousines = restaurantcousines;
	}
	public String getWebpage() {
		return webpage;
	}
	public void setWebpage(String webpage) {
		this.webpage = webpage;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongtitude() {
		return longtitude;
	}
	public void setLongtitude(Double longtitude) {
		this.longtitude = longtitude;
	}	
	public ArrayList<Integer> getJoinrestaurantcousie() {
		return joinrestaurantcousine;
	}
	public void setJoinrestaurantcousie(ArrayList<Integer> joinrestaurantcousie) {
		this.joinrestaurantcousine = joinrestaurantcousie;
	}
	public ArrayList<Integer> getJoinrestauranthrestaurantcategory() {
		return joinrestauranthrestaurantcategory;
	}
	public void setJoinrestauranthrestaurantcategory(
			ArrayList<Integer> joinrestauranthrestaurantcategory) {
		this.joinrestauranthrestaurantcategory = joinrestauranthrestaurantcategory;
	}
	public ArrayList<Integer> getJoinrestaurantcousine() {
		return joinrestaurantcousine;
	}
	public void setJoinrestaurantcousine(ArrayList<Integer> joinrestaurantcousine) {
		this.joinrestaurantcousine = joinrestaurantcousine;
	}
	public Set<Joinrestauranthrestaurantcategory> getRestaurantcategories() {
		return restaurantcategories;
	}
	public void setRestaurantcategories(
			Set<Joinrestauranthrestaurantcategory> restaurantcategories) {
		this.restaurantcategories = restaurantcategories;
	}
	public Hpricerange getPricerange() {
		return pricerange;
	}
	public void setPricerange(Hpricerange pricerange) {
		this.pricerange = pricerange;
	}
	public Hrestaurantlicence getRestaurantlicence() {
		return restaurantlicence;
	}
	public void setRestaurantlicence(Hrestaurantlicence restaurantlicence) {
		this.restaurantlicence = restaurantlicence;
	}
	public String getOrganisationname() {
		return organisationname;
	}
	public void setOrganisationname(String organisationname) {
		this.organisationname = organisationname;
	}
	public Restaurant () {
		
	}	
}
