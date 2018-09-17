package com.perscholas.model;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class OrderDetailInfo {
	private int id;
	private int bookid;
	private String bookName;
	private int quantity;
	private double unit_price;
	private double amount;
	
	public OrderDetailInfo() {}


	public OrderDetailInfo(int id, int bookid, String bookName, int quantity, double unit_price, double amount) {
		super();
		this.id = id;
		this.bookid = bookid;
		this.bookName = bookName;
		this.quantity = quantity;
		this.unit_price = unit_price;
		this.amount = amount;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getBookid() {
		return bookid;
	}


	public void setBookid(int bookid) {
		this.bookid = bookid;
	}


	public String getBookName() {
		return bookName;
	}


	public void setBookName(String bookName) {
		this.bookName = bookName;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public double getUnit_price() {
		return unit_price;
	}


	public void setUnit_price(double unit_price) {
		this.unit_price = unit_price;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
		
	}
}
