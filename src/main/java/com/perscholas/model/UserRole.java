package com.perscholas.model;

import javax.persistence.Entity;

import org.apache.commons.lang3.builder.EqualsBuilder;

@Entity
public class UserRole {
	
	//@Email
	private String userName;
	//@Size(min=6, max=15, message="The length of password must be between {2} and {1}")
	private String password;
	//@NotNull
	private int custId;
	//@NotNull
	private int role;
	
	private boolean enabled;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	
	public UserRole() {}
	


	public UserRole(String userName, String password, int custId, int role, boolean enabled, boolean accountNonExpired,
			boolean accountNonLocked, boolean credentialsNonExpired) {
		super();
		this.userName = userName;
		this.password = password;
		this.custId = custId;
		this.role = role;
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
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
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}	
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@Override
	public String toString() {
		return "User_Role [userName=" + userName + ", password=" + password + ", custId=" + custId + ", role=" + role
				+ "]";
	}


	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this,obj);
	}
	
	
	
}
