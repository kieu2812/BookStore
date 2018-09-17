package com.perscholas.auth;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.perscholas.controller.HomeController;
import com.perscholas.dao.CustomerDAO;
import com.perscholas.model.Customer;
import com.perscholas.model.ShoppingCart;
import com.perscholas.services.ShoppingCartService;


public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	CustomerDAO customerDAO;
	
	static Logger logger = Logger.getLogger(HomeController.class);

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		
		handle(request, response, authentication);
		clearAuthenticationAttributes(request);
		
	}

	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {

		logger.debug("Logged in successful and method handle() gets called.");
		String targetUrl = "";
		
		ShoppingCart myCart =  ShoppingCartService.getCart(request);
		logger.debug("myCart: " + myCart == null? "Shopping cart is empty":myCart);
		User u = (User) authentication.getPrincipal();
		
		String userName = u.getUsername();
		logger.info("getPrincipal: " + userName);
		
		//store username
		HttpSession session = request.getSession();
		session.setAttribute("userName", userName);
	
		Customer cust= customerDAO.getCustomerByEmail(userName);
		if(cust!=null) {
			session.setAttribute("custId", cust.getId());
			logger.info("custid =" +cust.getId());
		}
		//if shopping cart don't have any item to buy
		if(myCart != null && !myCart.getCartItems().isEmpty()){
			targetUrl = "/showMyCart";
		} else {
			targetUrl = determineTargetUrl(authentication);
		}

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(Authentication authentication) {
		boolean isUser = false;
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
				isUser = true;
				break;
			} else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}

		if (isUser) {
			return "/book/list";
		} else if (isAdmin) {
			return "/admin/book";
		} else {
			throw new IllegalStateException();
		}
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}
}