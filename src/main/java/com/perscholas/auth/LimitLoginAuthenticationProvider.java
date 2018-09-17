package com.perscholas.auth;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.perscholas.dao.impl.UserRoleDaoImpl;
import com.perscholas.model.UserAttempts;


public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider {
	static Logger log = Logger.getLogger(LimitLoginAuthenticationProvider.class);
	@Autowired
	UserRoleDaoImpl userRoleDao;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		try {
			
			//userRoleDao = new UserRoleService();

			System.out.println("authenticate");
			log.info("authenticate");
			
			Authentication auth = super.authenticate(authentication);
			
			System.out.println("authenticate successfully");
			log.info("authenticate successfully");

			
			// if reach here, means login success, else exception will be thrown
			// reset the user_attempts
			userRoleDao.resetFailAttempts(authentication.getName());
			
			System.out.println("resetFailAttempts successfully");
			log.info("resetFailAttempts successfully");

			return auth;

		} catch (BadCredentialsException e) {
			
			System.out.println("BadCredentialsException");
			log.info("BadCredentialsException");
			
			boolean isLocked = userRoleDao.updateFailAttempts(authentication.getName());
			log.info("User Account is locked? " + isLocked);
			if (isLocked) {
				log.info("User Account is locked - 1 ");
				throw new LockedException("User Account is locked!");
			} else {
				log.info("Bad Credentials Exception!");
				throw e;
			}

		} catch (LockedException e) {
			
			System.out.println("LockedException");
			log.info("LockedException");

			String error = "";
			UserAttempts userAttempts = userRoleDao.getUserAttempts(authentication.getName());
			if (userAttempts != null) {
				Date lastAttempts = userAttempts.getLastModified();
				error = "User account is locked! <br><br>Username : " + authentication.getName()
						+ "<br>Last Attempts : " + lastAttempts;
			} else {
				error = e.getMessage();
			}

			throw new LockedException(error);
		}

	}

	public UserRoleDaoImpl getUserRoleDao() {
		return userRoleDao;
	}

	public void setUserRoleDao(UserRoleDaoImpl userRoleDao) {
		this.userRoleDao = userRoleDao;
	}	

}