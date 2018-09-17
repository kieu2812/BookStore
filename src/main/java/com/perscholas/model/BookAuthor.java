package com.perscholas.model;

public class BookAuthor {
	private int bookid;
	private int authorid;
	
	public BookAuthor() {}

	public BookAuthor(int bookid, int authorid) {
		super();
		this.bookid = bookid;
		this.authorid = authorid;
	}

	public int getBookid() {
		return bookid;
	}

	public void setBookid(int bookid) {
		this.bookid = bookid;
	}

	public int getAuthorid() {
		return authorid;
	}

	public void setAuthorid(int authorid) {
		this.authorid = authorid;
	}

	@Override
	public String toString() {
		return "Book_Author [bookid=" + bookid + ", authorid=" + authorid + "]";
	}
	
	
}
