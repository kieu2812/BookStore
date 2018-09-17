package com.perscholas.services;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.perscholas.model.ShoppingCart;

@Service

public class ShoppingCartService {

	public static ShoppingCart getCart(HttpServletRequest request) {
		HttpSession session = request.getSession();
		ShoppingCart myCart = (ShoppingCart) session.getAttribute("shoppingCart");
		


		if(myCart==null) {
			myCart =  new ShoppingCart();
			request.getSession().setAttribute("shoppingCart", myCart);
		}
		return myCart;
	
	}
	public static void removeCart(HttpServletRequest request) {
		request.getSession().removeAttribute("shoppingCart");
	}
	
}
