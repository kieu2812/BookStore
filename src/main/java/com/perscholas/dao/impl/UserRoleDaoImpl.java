package com.perscholas.dao.impl;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.perscholas.model.UserAttempts;
import com.perscholas.model.UserRole;

//@Transactional("txHibernate")
@Repository
public class UserRoleDaoImpl {
	static Logger log = Logger.getLogger(UserRoleDaoImpl.class);
	
	private static final int MAX_ATTEMPTS = 3;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public UserRole getByUserName(String username) {
		Session session = null;
		List<UserRole> users = null;		
		try {		
			//session = sessionFactory.getCurrentSession();
			session = sessionFactory.openSession();
			Query query  = session.createQuery("from UserRole where username=?")
				.setParameter(0, username);
			users = query.list();
			
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
			//this.closeFactory();
		}
		
		return (users!=null && !users.isEmpty()) ? users.get(0) : null;
	}
	
	//@Transactional("txHibernate")
	public boolean updateFailAttempts(String username) {
		boolean isLocked = false;
	
		Session session = null;
		try {
			//session = this.sessionFactory.getCurrentSession();
			session = this.sessionFactory.openSession();

			if (this.getUserAttempts(username) == null) {// if no record, insert a new
				if(this.getByUserName(username) != null) {	
					log.info("insert new attempt");
					session.persist(new UserAttempts(username, 1, getCurrentTimeStamp()));
					log.info("insert new attempt successful");
				}
			} else {// update attempts count, + 1
				if(this.getByUserName(username) != null) {
					log.info("increase attempt 1");
					UserAttempts userAttempts = session.load(UserAttempts.class, username);
					log.debug("User Attempts: " + userAttempts);
					userAttempts.setAttempts(userAttempts.getAttempts()+1);
					userAttempts.setLastModified(getCurrentTimeStamp());
					session.update(userAttempts);
					log.info("User Attempts saved.");
										
					log.info("increase attempt 1 - successful");				
					log.debug("Attempts: " + userAttempts.getAttempts());
					
					if (userAttempts.getAttempts() >= MAX_ATTEMPTS) {
						// locked user			
						log.info("attempted 3 times -> User Account is locked");
						UserRole userRole = session.load(UserRole.class, username);
						log.debug("User Role: " + userRole);
						userRole.setAccountNonLocked(false);
						session.update(userRole);
						log.info("User Role saved.");
						
						isLocked = true;
					}				
				}
			}
			
		}
		catch (Exception e) {
			log.error("updateFailAttempts" + e.getMessage());
			throw e;
		} finally {		
			
			if (session != null) {
				session.close();
			}
			//this.closeFactory();
			
		}
		
		log.debug("isLocked? " + isLocked);
		return isLocked;
	}
	
	public void resetFailAttempts(String username) {
		Session session = null;
		try {
			
			log.info("reset fail attempts");
			
			UserAttempts user = this.getUserAttempts(username);	
			
			if (user != null) {
				//session = this.sessionFactory.getCurrentSession();
				session = this.sessionFactory.openSession();
				session.evict(user);
				user.setAttempts(0);
				user.setLastModified(getCurrentTimeStamp());
				session.update(user);
				
				log.info("reset fail attempts successful");
			}
			
		}
		catch (Exception e) {
			log.error("Error at resetFailAttempts: " + e.getMessage());
		} finally {
			
			if (session != null) {
				session.close();
			}
			//this.closeFactory();
			
		}
	}

	public UserAttempts getUserAttempts(String username) {
		Session session = null;
		List<UserAttempts> users = null;
		try {
			//session = sessionFactory.getCurrentSession();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from UserAttempts where username=?")
				.setParameter(0, username);
			
			users = Collections.checkedList(query.list(), UserAttempts.class);
			
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
			//this.closeFactory();
		}
		
		
		return (users != null && !users.isEmpty()) ? users.get(0) : null;
	}	
	
	public void closeFactory() {
	    if (sessionFactory != null) {
	        try {
	            sessionFactory.close();
	        } catch (HibernateException ignored) {
	            log.error("Couldn't close SessionFactory", ignored);
	        }
	    }
	}
	
	private java.sql.Timestamp getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());

	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
}
