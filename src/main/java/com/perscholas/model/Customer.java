package com.perscholas.model;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class Customer {
	@NotNull
	@Id
	private int id;

//	@NotNull
//	@Pattern(regexp="^\\w*$", message="First name must be alphabet")
	private String firstName;
	
//	@NotNull
//	@Pattern(regexp="^\\w*$", message="Last name must be alphabet")
	private String lastName;
	
//	@NotNull
//	@Pattern(regexp="^\\d+\\w\\s*$", message="Address must include number, alphabet and spaces")
	private String address;
	
//	@NotNull
	private String city;
//	@NotNull
	private String state;
//	@Email
	private String email;
//	@Pattern(regexp="\\[0-9]{5,7}")
	private String zipcode;
	public Customer() {}

	public Customer(int id, String firstName, String lastName, String address, String city, String state, String zipcode,
			String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.email = email;
		this.zipcode=zipcode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address
				+ ", city=" + city + ", state=" + state + ", email=" + email + ", zipcode=" + zipcode + "]";
	}

	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
		
	}
}

