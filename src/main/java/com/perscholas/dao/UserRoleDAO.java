package com.perscholas.dao;

import java.util.List;

import com.perscholas.model.UserAttempts;
import com.perscholas.model.UserRole;

public interface UserRoleDAO {
	enum SQL{
		GET_ALL_USER("Select USERNAME, PASSWORD, CUSTOMERID, ROLE from user_role"),
		GET_USER_BY_USER_NAME("SELECT  USERNAME, PASSWORD, CUSTOMERID, ROLE, ENABLED, ACCOUNTNONEXPIRED, ACCOUNTNONLOCKED, CREDENTIALSNONEXPIRED FROM USER_ROLE WHERE USERNAME=?"),
		GET_ALL_USERS_BY_ROLE("SELECT * FROM USER_ROLE WHERE ROLE= ?"),
		INSERT_USER_ROLE("INSERT INTO USER_ROLE(USERNAME, PASSWORD, CUSTOMERID, ROLE) VALUES(?,?,?,?)"),
		CHANGE_PASSWORD_BY_USER_NAME("UPDATE USER_ROLE SET PASSWORD=? WHERE USERNAME=? AND PASSWORD =? "),
		RESET_PASSWORD_BY_USER_NAME("UPDATE USER_ROLE SET PASSWORD=? WHERE USERNAME=?"),
		CHANGE_ROLE("UPDATE USER_ROLE SET ROLE=? WHERE USERNAME= ?"),
//		DELETE_USER_ROLE_BY_ID("DELETE FROM USER_ROLE WHERE USER_NAME=?"),
		
		SQL_USERS_UPDATE_LOCKED ("UPDATE USER_ROLE SET accountNonLocked = ? WHERE username = ?"),
		SQL_USERS_COUNT("SELECT count(*) FROM USER_ROLE WHERE username = ?"),
		SQL_USER_ATTEMPTS_GET("SELECT * FROM USER_ATTEMPTS WHERE username = ?"),
		SQL_USER_ATTEMPTS_INSERT("INSERT INTO USER_ATTEMPTS (USERNAME, ATTEMPTS, LASTMODIFIED) VALUES(?,?,sysdate)"),
		SQL_USER_ATTEMPTS_UPDATE_ATTEMPTS("UPDATE USER_ATTEMPTS SET attempts = attempts + 1, lastmodified = sysdate WHERE username = ?"),
		SQL_USER_ATTEMPTS_RESET_ATTEMPTS( "UPDATE USER_ATTEMPTS SET attempts = 0, lastmodified = sysdate WHERE username = ?"),
		
		CHECK_VALID_USER("SELECT * FROM USER_ROLE WHERE USERNAME=? AND PASSWORD=?");
		private final String query;
		private SQL(String s){
			this.query= s;
		}
		public String getQuery() {
			return this.query;
		}
		
	}
	
	public List<UserRole> getAllUsers();
	public UserRole getByUserName(String user_name);
	public boolean addUser(UserRole user_role);
	public List<UserRole> getAllUsersByRole(int role);
	public boolean updateUser(String username, String oldPassword, String newPassword);
//	public boolean removeUser(String username);
//	public List<User_Role> findByName(String user_name);
	public boolean checkValidUser(String user_name, String password) ;
	public boolean changeRole(String username, int role);
	
	public void updateFailAttempts(String username);
	public void resetFailAttempts(String username);	
	UserAttempts getUserAttempts(String username);
	public String resetPassword(String username);
}
