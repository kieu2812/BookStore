package com.perscholas.model;

import java.time.LocalDate;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class OrderDetail {
	private int id;
	private int orderId;
	private int bookid;
	private int quantity;
	private double unit_price;
	private LocalDate shipping_date;
	private LocalDate expect_date;
	
	public OrderDetail() {}
	public OrderDetail(int id, int orderId, int bookid, int quantity, double unit_price, LocalDate shipping_date,
			LocalDate expect_date) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.bookid = bookid;
		this.quantity = quantity;
		this.unit_price = unit_price;
		this.shipping_date = shipping_date;
		this.expect_date = expect_date;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getBookid() {
		return bookid;
	}
	public void setBookid(int bookid) {
		this.bookid = bookid;
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
	public LocalDate getShipping_date() {
		return shipping_date;
	}
	public void setShipping_date(LocalDate shipping_date) {
		this.shipping_date = shipping_date;
	}
	public LocalDate getExpect_date() {
		return expect_date;
	}
	public void setExpect_date(LocalDate expect_date) {
		this.expect_date = expect_date;
	}

	
	@Override
	public String toString() {
		return "OrderDetail [id=" + id + ", orderId=" + orderId + ", bookid=" + bookid 
				+ ", quantity=" + quantity + ", unit_price=" + unit_price + ", shipping_date=" + shipping_date
				+ ", expect_date=" + expect_date + "]";
	}

	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
		
	}
}
