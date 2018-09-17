package com.perscholas.auth;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.perscholas.dao.impl.UserRoleDaoImpl;
import com.perscholas.model.UserRole;



/**
 * Reference org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl
 * 
 * @author 
 * 
 */
public class CustomUserDetailsService implements UserDetailsService {
	
	static Logger log = Logger.getLogger(CustomUserDetailsService.class);
	
	@Autowired
	UserRoleDaoImpl userRoleDao;
	
	
	//override to pass get accountNonLocked  
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//userRoleDao = new UserRoleService();
		
		System.err.println("input username: " + username);
		log.info("input username: " + username);
		
		System.out.println("loadUserByUsername");
		log.info("loadUserByUsername: " + userRoleDao);
		
        UserRole user = userRoleDao.getByUserName(username);
        
        System.out.println("after call DAO: " + user.getRole());
		log.info("after call DAO");
        
      //  System.out.println("Exception occurred");
        if (user == null) {
        	System.out.println("Exception occurred");
        	log.info("user is null");
            throw new UsernameNotFoundException(username);
        }else {
        
        System.out.println("loadUserByUsername successfull");
        log.info("loadUserByUsername successfull");
        
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        String role = user.getRole()==1? "ROLE_ADMIN":"ROLE_USER";
        log.info("ROLE: "+role);
        authorities.add(new SimpleGrantedAuthority(role));
        
		return new User(user.getUserName(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(),
				user.isCredentialsNonExpired(), user.isAccountNonLocked(), authorities);
        }
    }


	public UserRoleDaoImpl getUserRoleDao() {
		return userRoleDao;
	}


	public void setUserRoleDao(UserRoleDaoImpl userRoleDao) {
		this.userRoleDao = userRoleDao;
	}


}