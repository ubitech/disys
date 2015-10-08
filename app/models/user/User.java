package models.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.avaje.ebean.Page;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class User extends Model {
		
	public User() {
		
	}
	public User(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}
	@Id
	Long id;
	String username;
	String password;
	String role;
	String name;
	String surname;
	String phone;
	String email;
	Integer hascompletedquestionnaire;
	
	@OneToOne(mappedBy="user")
	@JoinColumn(name="id")
	Profile profile;
	
	@OneToOne(mappedBy="user")
	@JoinColumn(name="id")
	Ownerpreference ownerpreference;	
	
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	public static Finder<Long, User> find = new Finder<Long, User>(Long.class, User.class);
	
	public static Page<User> find(int page) {
	    return 
	            find.where()
	                .orderBy("id asc")
	                .findPagingList(10)
	                .setFetchAhead(false)
	                .getPage(page);
	}	
  
	public static User findbyUsername(String username) {
		return find.where().eq("username", username).findUnique();
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
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
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getHascompletedquestionnaire() {
		return hascompletedquestionnaire;
	}
	public void setHascompletedquestionnaire(Integer hascompletedquestionnaire) {
		this.hascompletedquestionnaire = hascompletedquestionnaire;
	}	
}
