package com.perscholas.model;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class ShoppingCartIem {
	
	private BookInfo bookInfo;
	private int quantity;
	
	public ShoppingCartIem() {
		super();
		// 
	}
	public ShoppingCartIem(BookInfo bookInfo, int quantity) {
		super();
		this.bookInfo = bookInfo;
		this.quantity = quantity;
	}
	public BookInfo getBookInfo() {
		return bookInfo;
	}
	public void setBookInfo(BookInfo bookInfo) {
		this.bookInfo = bookInfo;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	/**
	 * 
	 * @return amount of this item in cart.
	 */
	public double getAmount() {
		return this.quantity* bookInfo.getPrice();
	}
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this,obj);
	}
}
