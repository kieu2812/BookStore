package com.perscholas.model;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class ShippingAddress {
	private int id;
	private String address;
	private String city;
	private String state;
	private String zipcode;
	public ShippingAddress() {
		super();
		// 
	}
	public ShippingAddress(int id, String address, String city, String state, String zipcode) {
		super();
		this.id = id;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	@Override
	public String toString() {
		return "Shipping_Address [id=" + id + ", address=" + address + ", city=" + city + ", state=" + state
				+ ", zipcode=" + zipcode + "]";
	}
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
		
	}
}
