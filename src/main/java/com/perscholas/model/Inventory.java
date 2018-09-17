package com.perscholas.model;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class Inventory {
	private String barcode;
	private int bookid;
	private boolean isAvailable;
	
	public Inventory() {}
	
	public Inventory(String barcode, int bookid, boolean isAvailable) {
		super();
		this.barcode = barcode;
		this.bookid = bookid;
		this.isAvailable = isAvailable;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public int getBookid() {
		return bookid;
	}

	public void setBookid(int bookid) {
		this.bookid = bookid;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Override
	public String toString() {
		return "Inventory [barcode=" + barcode + ", bookid=" + bookid + ", isAvailable=" + isAvailable + "]";
	}
	
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
		
	}
}
