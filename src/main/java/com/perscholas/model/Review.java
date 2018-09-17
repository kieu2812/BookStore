package com.perscholas.model;

import java.sql.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class Review {
	private int id;
	private int bookid;
	private int posterid;
	private String posterName;
	private String comments;
	private Date createDate;
	private int rating;
	
	
	
	public Review() {
		super();
	
	}
	public Review(int id, int bookid, int posterid, String posterName, String comments, Date createDate, int rating) {
		super();
		this.id = id;
		this.bookid = bookid;
		this.posterid = posterid;
		this.posterName = posterName;
		this.comments = comments;
		this.createDate = createDate;
		this.rating = rating;
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
	public int getPosterid() {
		return posterid;
	}
	public void setPosterid(int posterid) {
		this.posterid = posterid;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public String getPosterName() {
		return posterName;
	}
	public void setPosterName(String posterName) {
		this.posterName = posterName;
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", bookid=" + bookid + ", posterid=" + posterid + ", posterName=" + posterName
				+ ", comments=" + comments + ", createDate=" + createDate + ", rating=" + rating + "]";
	}
	public boolean equal(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
}
