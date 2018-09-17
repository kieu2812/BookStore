package com.perscholas.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ShoppingConfirmForm {
	
	private int orderNumber;
	private Customer customer;
	private Map<Integer, ShoppingCartIem> cartItems;
	private CustomerCard customerCard;
	private ShippingAddress shippingAddress;
	private String sameAddress;
	
	public ShoppingConfirmForm(int orderNumber, Customer customer, Map<Integer, ShoppingCartIem> cartItems,
			CustomerCard customerCard, ShippingAddress shippingAddress, String sameAddress) {
		super();
		this.orderNumber = orderNumber;
		this.customer = customer;
		this.cartItems = cartItems;
		this.customerCard = customerCard;
		this.shippingAddress = shippingAddress;
		this.sameAddress = sameAddress;
	}


	public ShoppingConfirmForm() {
		super();
		
	}

	
	public ShippingAddress getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(ShippingAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getSameAddress() {
		return sameAddress;
	}

	public void setSameAddress(String sameAddress) {
		this.sameAddress = sameAddress;
	}

	

	public CustomerCard getCustomerCard() {
		return customerCard;
	}


	public void setCustomerCard(CustomerCard customerCard) {
		this.customerCard = customerCard;
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
	
	
}
