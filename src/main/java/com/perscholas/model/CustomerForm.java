package com.perscholas.model;

public class CustomerForm {
	
	private Customer customer;
	
	//@NotNull
	//@Size(min=6, max=15)
	private String password;
	
	public CustomerForm() {
		super();
		// 
	}
	public CustomerForm(Customer customer, String password) {
		super();
		this.customer = customer;
		this.password = password;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "CustomerForm [customer=" + customer + ", password=" + password + "]";
	}
	
}
