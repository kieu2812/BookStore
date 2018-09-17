package com.perscholas.model;

import org.apache.commons.lang3.builder.EqualsBuilder;

// this class use for shopping cart item and order detail
public class BookInfo {
	private int id;
	private String name;
	private double price;
	private String hardCoverPath;
	private int qtyInStock;
	private double rating;
	private int totalReview;
	public BookInfo() {
		super();
	}

	
	public BookInfo(int id, String name, double price, String hardCoverPath, int qtyInStock, double rating,
			int totalReview) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.hardCoverPath = hardCoverPath;
		this.qtyInStock = qtyInStock;
		this.rating = rating;
		this.totalReview = totalReview;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getHardCoverPath() {
		return hardCoverPath;
	}
	public void setHardCoverPath(String hardCoverPath) {
		this.hardCoverPath = hardCoverPath;
	}
	
	public int getQtyInStock() {
		return qtyInStock;
	}


	public void setQtyInStock(int qtyInStock) {
		this.qtyInStock = qtyInStock;
	}


	public double getRating() {
		return rating;
	}


	public void setRating(double rating) {
		this.rating = rating;
	}


	public int getTotalReview() {
		return totalReview;
	}


	public void setTotalReview(int totalReview) {
		this.totalReview = totalReview;
	}


	@Override
	public String toString() {
		return "BookInfo [id=" + id + ", name=" + name + ", price=" + price + ", hardCoverPath=" + hardCoverPath
				+ ", qtyInStock=" + qtyInStock + ", rating=" + rating + ", totalReview=" + totalReview + "]";
	}

	@Override
	public boolean equals(Object obj) {
	
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	
	
}
