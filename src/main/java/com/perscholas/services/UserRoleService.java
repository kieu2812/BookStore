package com.perscholas.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.perscholas.auth.CustomUserDetailsService;
import com.perscholas.dao.AbstractDAO;
import com.perscholas.dao.UserRoleDAO;
import com.perscholas.model.UserAttempts;
import com.perscholas.model.UserRole;

@Service
public class UserRoleService extends AbstractDAO implements UserRoleDAO {
	static Logger log = Logger.getLogger(UserRoleService.class);

	@Autowired
	BCryptPasswordEncoder encoder;
	private static final int MAX_ATTEMPTS = 3;
	@Autowired
	UserRoleDAO userRoleDAO;

	@Override
	public List<UserRole> getAllUsers() {

		List<UserRole> users = null;
		PreparedStatement ps = null;
		ResultSet rs = null;


		try {
			super.getConnection();
			ps = conn.prepareStatement(SQL.GET_ALL_USER.getQuery());
			rs = ps.executeQuery();
			users = new ArrayList<>();
			while (rs.next()) {
				UserRole tempUser = new UserRole();
				tempUser.setUserName(rs.getString("USERNAME"));
				tempUser.setPassword(rs.getString("PASSWORD"));
				tempUser.setCustId(rs.getInt("CUSTOMERID"));
				tempUser.setRole(rs.getInt("ROLE"));

				users.add(tempUser);
			}
		} catch (SQLException e) {

			log.error(String.format("Error at getAllUsers %s" , e.getMessage() ));

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {
				log.error(String.format("Error at getAllUsers %s" , e.getMessage() ));

			}

		}
		return users;

	}

	@Override
	public UserRole getByUserName(String userName) {

		UserRole user = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		log.info("input: " + userName);
		try {
			super.getConnection();
			ps = conn.prepareStatement(SQL.GET_USER_BY_USER_NAME.getQuery());
			// ps= conn.prepareStatement("select * from userRole where username=");

			ps.setString(1, userName);
			rs = ps.executeQuery();

			while (rs.next()) {
				// USERNAME, PASSWORD, CUSTOMERID, ROLE
				// log.info("execute success. " + rs.next());
				user = new UserRole();
				log.info("Customer: " + rs.getInt(3));

				user.setUserName(rs.getString(1));
				user.setPassword(rs.getString(2));
				user.setCustId(rs.getInt(3));
				user.setRole(rs.getInt(4));
				user.setEnabled(rs.getBoolean(5));
				user.setAccountNonExpired(rs.getBoolean(6));
				user.setAccountNonLocked(rs.getBoolean(7));
				user.setCredentialsNonExpired(rs.getBoolean(8));

			}

		} catch (SQLException e) {

			log.error(String.format("Error at getByUserName %s" , e.getMessage() ));

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();

			} catch (SQLException e) {
				log.error(String.format("Error at getByUserName %s" , e.getMessage() ));

			}

			super.closeConnection();
		}
		return user;
	}

	@Override
	public boolean addUser(UserRole userRole) {

		PreparedStatement ps = null;
		boolean isInserted = false;
		try {
			super.getConnection();
			ps = conn.prepareStatement(SQL.INSERT_USER_ROLE.getQuery());

			ps.setString(1, userRole.getUserName());
			ps.setString(2, userRole.getPassword());
			ps.setInt(3, userRole.getCustId());
			ps.setInt(4, userRole.getRole());

			isInserted = ps.executeUpdate() > 0 ? true : false;

		} catch (SQLException e) {

			log.error(String.format("Error at addUser %s" , e.getMessage() ));

		} finally {
			try {
				if (ps != null)
					ps.close();

			} catch (SQLException e) {
				log.error(String.format("Error at addUser %s" , e.getMessage() ));

			}

			super.closeConnection();
		}
		return isInserted;
	}

	@Override
	public boolean updateUser(String username, String oldPassword, String newPassword) {

		boolean isUpdated = false;

		UserRole user = userRoleDAO.getByUserName(username);
		// check username
		if (user == null)
			return false;
		// check username and password is valid
		if (encoder.matches(oldPassword, user.getPassword())) {

			PreparedStatement ps = null;

			try {
				super.getConnection();
				ps = conn.prepareStatement(SQL.CHANGE_PASSWORD_BY_USER_NAME.getQuery());

				ps.setString(1, encoder.encode(newPassword));
				ps.setString(2, username);
				ps.setString(3, user.getPassword());

				isUpdated = ps.executeUpdate() > 0 ? true : false;

			} catch (SQLException e) {

				log.error(String.format("Error at updateUser %s" , e.getMessage() ));
			} finally {
				try {
					if (ps != null)
						ps.close();

				} catch (SQLException e) {
					log.error(String.format("Error at updateUser %s" , e.getMessage() ));

				}

				super.closeConnection();
			}
		}
		return isUpdated;
	}

	// @Override
	// public boolean removeUser(String username) {

	// boolean isDeleted= false;
	//
	// PreparedStatement ps= null;
	//
	// try {
	// super.getConnection();
	// ps= conn.prepareStatement(SQL.DELETE_USER_ROLE_BY_ID.getQuery());
	//
	// ps.setString(1, username);
	// isDeleted = ps.executeUpdate() >0 ? true : false;
	//
	// } catch (SQLException e) {
	//
	// log.info(e.getMessage());
	// }finally {
	//
	// super.closeConnection();
	// }
	//
	// return isDeleted;
	// }

	@Override
	public boolean checkValidUser(String userName, String password) {
		boolean isValid = false;

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			super.getConnection();
			ps = conn.prepareStatement(SQL.CHECK_VALID_USER.getQuery());

			ps.setString(1, userName);
			ps.setString(2, password);

			rs = ps.executeQuery();
			if (rs.next())
				isValid = true;

		} catch (SQLException e) {

			log.error(String.format("Error at getAllByOrderId %s" , e.getMessage() ));

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();

			} catch (SQLException e) {
				log.error(String.format("Error at getAllByOrderId %s" , e.getMessage() ));

			}

			super.closeConnection();
		}

		return isValid;
	}

	@Override
	public List<UserRole> getAllUsersByRole(int role) {
		List<UserRole> users = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			super.getConnection();
			ps = conn.prepareStatement(SQL.GET_ALL_USERS_BY_ROLE.getQuery());
			ps.setInt(1, role);
			rs = ps.executeQuery();
			users = new ArrayList<>();
			while (rs.next()) {
				UserRole tempUser = new UserRole();
				tempUser.setUserName(rs.getString("USERNAME"));
				tempUser.setPassword(rs.getString("PASSWORD"));
				tempUser.setCustId(rs.getInt("CUSTOMERID"));
				tempUser.setRole(rs.getInt("ROLE"));

				users.add(tempUser);
			}
		} catch (SQLException e) {

			log.error(String.format("Error at getAllUsersByRole %s" , e.getMessage() ));
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();

			} catch (SQLException e) {
				log.error(String.format("Error at getAllUsersByRole %s" , e.getMessage() ));

			}

			super.closeConnection();
		}
		return users;
	}

	@Override
	public boolean changeRole(String username, int role) {
		boolean isUpdated = false;
		PreparedStatement ps = null;
		try {
			super.getConnection();
			ps = conn.prepareStatement(SQL.CHANGE_ROLE.getQuery());
			ps.setInt(1, role);
			ps.setString(2, username);
			isUpdated = ps.executeUpdate() > 0 ? true : false;
		} catch (SQLException e) {

			log.error(String.format("Error at changeRole %s" , e.getMessage() ));

		} finally {
			try {
				if (ps != null)
					ps.close();

			} catch (SQLException e) {
				log.error(String.format("Error at changeRole %s" , e.getMessage() ));

			}

			super.closeConnection();
		}

		return isUpdated;
	}

	@Override
	public void updateFailAttempts(String username) {

		PreparedStatement ps = null;

		try {

			UserAttempts user = this.getUserAttempts(username);
			if (user == null) {// if no record, insert a new
				if (this.getByUserName(username) != null) {

					super.getConnection();
					log.info("Connection successful");

					ps = conn.prepareStatement(SQL.SQL_USER_ATTEMPTS_INSERT.getQuery());

					log.info("prepareStatement successfull" + ps);

					ps.setString(1, username);
					ps.setInt(2, 1);
					ps.executeUpdate();

					log.info("insert new attempt successful");
				}
			} else {// update attempts count, +1
				if (this.getByUserName(username) != null) {
					log.info("increase attempt 1");

					super.getConnection();
					log.info("Connection successful");

					ps = conn.prepareStatement(SQL.SQL_USER_ATTEMPTS_UPDATE_ATTEMPTS.getQuery());
					ps.setString(1, username);
					ps.executeUpdate();

					log.info("increase attempt 1 - successful");
				}

				if (user.getAttempts() + 1 >= MAX_ATTEMPTS) {
					// locked user
					log.info("attempted 3 times -> User Account is locked");

					super.getConnection();
					log.info("Connection successful");

					ps = conn.prepareStatement(SQL.SQL_USERS_UPDATE_LOCKED.getQuery());
					ps.setBoolean(1, false);
					ps.setString(2, username);
					ps.executeUpdate();
					// throw exception
					throw new LockedException("User Account is locked!");
				}
			}
		} catch (SQLException e) {
			log.error(String.format("Error at updateFailAttempts %s",e.getMessage()));
		} finally {
			try {
				if (ps != null)
					ps.close();

			} catch (SQLException e) {
				log.error(String.format("Error at updateFailAttempts %s",e.getMessage()));
			}

			super.closeConnection();
		}
	}

	@Override
	public void resetFailAttempts(String username) {

		PreparedStatement ps = null;

		try {
			super.getConnection();
			ps = conn.prepareStatement(SQL.SQL_USER_ATTEMPTS_RESET_ATTEMPTS.getQuery());
			ps.setString(1, username);
			ps.executeUpdate();
		} catch (SQLException e) {

			log.error(String.format("Error at resetFailAttempts %s",e.getMessage()));
		} finally {
			try {
				if (ps != null)
					ps.close();

			} catch (SQLException e) {
				log.error(String.format("Error at resetFailAttempts %s",e.getMessage()));
			}

			super.closeConnection();
		}
	}

	@Override
	public UserAttempts getUserAttempts(String username) {

		UserAttempts user = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			super.getConnection();
			ps = conn.prepareStatement(SQL.SQL_USER_ATTEMPTS_GET.getQuery());
			ps.setString(1, username);
			rs = ps.executeQuery();

			while (rs.next()) {
				user = new UserAttempts();
				user.setUserName(rs.getString("USERNAME"));
				user.setAttempts(rs.getInt("ATTEMPTS"));
				user.setLastModified(rs.getTimestamp("LASTMODIFIED"));
			}

		} catch (SQLException e) {

			log.error(String.format("Error at getUserAttempts %s",e.getMessage()));
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();

			} catch (SQLException e) {
				log.error(String.format("Error at getUserAttempts %s",e.getMessage()));
			}

			super.closeConnection();
		}
		return user;
	}

	@Override
	public String resetPassword(String username) {
		String newPassword = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			super.getConnection();
			ps = conn.prepareStatement(SQL.RESET_PASSWORD_BY_USER_NAME.getQuery());
			int length = 10;
			boolean useLetters = true;
			boolean useNumbers = true;
			newPassword = RandomStringUtils.random(length, useLetters, useNumbers);
			ps.setString(1, encoder.encode(newPassword));
			ps.setString(2, username);
			rs = ps.executeQuery();

		} catch (SQLException e) {

			log.error(String.format("Error at resetPassword %s",e.getMessage()));
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();

			} catch (SQLException e) {
				log.error(String.format("Error at resetPassword %s",e.getMessage()));
			}

			super.closeConnection();
		}

		return newPassword;
	}
}
