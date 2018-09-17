package com.perscholas.model;

public class UserForm {
	
//	@NotNull
//	@Pattern(regexp ="^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")
	private String userName;
//	@NotNull
//	@Size(min=8, max=15, message="Password must have a length between {2} and {1}")
	private String password;
	public UserForm() {}
	
	
	public UserForm(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "UserForm [userName=" + userName + ", password=" + password + "]";
	}
	
}
