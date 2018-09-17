package com.perscholas.model;

public class FormChangePassword {
	private String userName;
	private String password;
	private String newPass;
	private String confirmPass;
	
	public FormChangePassword() {}
	public FormChangePassword(String userName, String password, String newPass, String confirmPass) {
		super();
		this.userName = userName;
		this.password = password;
		this.newPass = newPass;
		this.confirmPass = confirmPass;
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
	public String getNewPass() {
		return newPass;
	}
	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}
	public String getConfirmPass() {
		return confirmPass;
	}
	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}
	@Override
	public String toString() {
		return "FormChangePassword [userName=" + userName + ", password=" + password + ", newPass=" + newPass
				+ ", confirmPass=" + confirmPass + "]";
	}
	
	
}
