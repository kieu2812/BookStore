package com.perscholas.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ShoppingCart {
	
	private int orderNumber;
	private Customer customer;
	private ShippingAddress shippingAddress;
	private Map<Integer, ShoppingCartIem> cartItems;

	private CustomerCard customerCard;
	
	public ShoppingCart() {
		super();
		// 
	}

	
	
	public ShoppingCart(int orderNumber, Customer customer, ShippingAddress shippingAddress,
			Map<Integer, ShoppingCartIem> cartItems, CustomerCard customerCard) {
		super();
		this.orderNumber = orderNumber;
		this.customer = customer;
		this.shippingAddress = shippingAddress;
		this.cartItems = cartItems;
		this.customerCard = customerCard;
	}



	public CustomerCard getCustomerCard() {
		return customerCard;
	}



	public void setCustomerCard(CustomerCard customerCard) {
		this.customerCard = customerCard;
	}



	public ShippingAddress getShippingAddress() {
		return shippingAddress;
	}


	public void setShippingAddress(ShippingAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public int getOrderNumber() {
		
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Map<Integer, ShoppingCartIem> getCartItems() {
		if(cartItems==null)
			return  new HashMap<Integer, ShoppingCartIem>();
		return cartItems;
	}
	public void setCartItems(Map<Integer, ShoppingCartIem> cartItems) {
		this.cartItems = cartItems;
	}
	@Override
	public String toString() {
		return "ShoppingCart [orderNumber=" + orderNumber + ", customer=" + customer + ", shippingAddress="
				+ shippingAddress + ", cartItems=" + cartItems + "]";
	}
	
}
