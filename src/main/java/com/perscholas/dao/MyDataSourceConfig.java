package com.perscholas.dao;

public class MyDataSourceConfig {
	private String driver;
	private String url;
	private String username;
	private String password;
	public MyDataSourceConfig() {
		super();
		// 
	}
	public MyDataSourceConfig(String driver, String url, String username, String password) {
		super();
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	@Override
	public String toString() {
		return "MyDataSourceConfig [driver=" + driver + ", url=" + url + ", username=" + username + ", password="
				+ password + "]";
	}
	
}
