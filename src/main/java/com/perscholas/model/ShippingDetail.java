package com.perscholas.model;

import java.sql.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class ShippingDetail {
	private int id;
	private int orderid;
	private String barcode;
	private Date returnDate;
	public ShippingDetail() {
		super();
		// 
	}
	public ShippingDetail(int id, int orderid, String barcode, Date returnDate) {
		super();
		this.id = id;
		this.orderid = orderid;
		this.barcode = barcode;
		this.returnDate = returnDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	@Override
	public String toString() {
		return "Shipping_Detail [id=" + id + ", orderid=" + orderid + ", barcode=" + barcode + ", returnDate="
				+ returnDate + "]";
	}
	
	
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
		
	}
}
