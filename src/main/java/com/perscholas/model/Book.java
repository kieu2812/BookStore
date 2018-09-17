package com.perscholas.model;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class Book {
	private int id;
	private String name;
	private String hardCoverPath;
	private int authorId;
	private int publisherId;
	private String ISBN_10;
	private String ISBN_13;
	private int categoryId;
	private double price;
	private String description;
	private int pages;
	private int deleteflag;
	private int qtyInStock;
	private float rating;
	private int totalReview;
	
	
	public Book() {}


	public Book(int id, String name, String hardCoverPath, int authorId, int publisherId, String iSBN_10,
			String iSBN_13, int categoryId, double price, String description, int pages, int deleteflag, int qtyInStock,
			float rating, int totalReview) {
		super();
		this.id = id;
		this.name = name;
		this.hardCoverPath = hardCoverPath;
		this.authorId = authorId;
		this.publisherId = publisherId;
		ISBN_10 = iSBN_10;
		ISBN_13 = iSBN_13;
		this.categoryId = categoryId;
		this.price = price;
		this.description = description;
		this.pages = pages;
		this.deleteflag = deleteflag;
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


	public String getHardCoverPath() {
		return hardCoverPath;
	}


	public void setHardCoverPath(String hardCoverPath) {
		this.hardCoverPath = hardCoverPath;
	}


	public int getAuthorId() {
		return authorId;
	}


	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}


	public int getPublisherId() {
		return publisherId;
	}


	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}


	public String getISBN_10() {
		return ISBN_10;
	}


	public void setISBN_10(String iSBN_10) {
		ISBN_10 = iSBN_10;
	}


	public String getISBN_13() {
		return ISBN_13;
	}


	public void setISBN_13(String iSBN_13) {
		ISBN_13 = iSBN_13;
	}


	public int getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getPages() {
		return pages;
	}


	public void setPages(int pages) {
		this.pages = pages;
	}


	public int getDeleteflag() {
		return deleteflag;
	}


	public void setDeleteflag(int deleteflag) {
		this.deleteflag = deleteflag;
	}


	public int getQtyInStock() {
		return qtyInStock;
	}


	public void setQtyInStock(int qtyInStock) {
		this.qtyInStock = qtyInStock;
	}


	public float getRating() {
		return rating;
	}


	public void setRating(float rating) {
		this.rating = rating;
	}


	public int getTotalReview() {
		return totalReview;
	}


	public void setTotalReview(int totalReview) {
		this.totalReview = totalReview;
	}


	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
		
	}


	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", hardCoverPath=" + hardCoverPath + ", authorId=" + authorId
				+ ", publisherId=" + publisherId + ", ISBN_10=" + ISBN_10 + ", ISBN_13=" + ISBN_13 + ", categoryId="
				+ categoryId + ", price=" + price + ", description=" + description + ", pages=" + pages
				+ ", deleteflag=" + deleteflag + ", qtyInStock=" + qtyInStock + ", rating=" + rating + ", totalReview="
				+ totalReview + "]";
	}
	
}
