package com.perscholas.model;

import java.util.Date;

public class UserAttempts {
	private String userName;
	private int attempts;
	private Date lastModified;
	
	public UserAttempts() {}
	
	public UserAttempts(String userName, int attempts) {
		this.userName = userName;
		this.attempts = attempts;
	}
	
	public UserAttempts(String userName, int attempts, Date lastModified) {
		this.userName = userName;
		this.attempts = attempts;
		this.lastModified = lastModified;
	}	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	};
	
	
}
