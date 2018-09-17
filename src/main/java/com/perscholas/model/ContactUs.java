package com.perscholas.model;

public class ContactUs {
	private String firstName;
	private String lastName;
	private String email;
	private String subject;
	private String message;
	
	public ContactUs() {
		
	}
	public ContactUs(String firstName, String lastName, String email, String subject, String message) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.subject = subject;
		this.message = message;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
