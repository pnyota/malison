package com.malison.common.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.ws.rs.FormParam;

import com.malison.common.model.BaseEntity;

@NamedQueries({
	@NamedQuery(
		name = "User.findUser",
		query = "SELECT u FROM User u WHERE u.userName = :username"
	),
	@NamedQuery(
			name ="User.getUsernames",
			query = "SELECT u.userName from User u"
			)
})

@Entity
@Table (name = "USER")
public class User extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@FormParam("firstname")
	@Column (name = "FIRST")
	private String firstName;
	
	@FormParam("lastname")	
	@Column (name = "LAST")
	private String lastName;
	
	@FormParam("username")
	@Column (name = "USER", unique = true)
	private String userName;
	
	@FormParam("position")
	@Column (name = "POSITION")
	private String position;
	
	@FormParam("password")
	@Column (name = "PASSWORD")
	private String password;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

}
