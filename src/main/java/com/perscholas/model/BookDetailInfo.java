package com.perscholas.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class BookDetailInfo{
	private Book book;
	private String author;
	private String publisher;
	private String category;
	@Autowired
	private List<Review> listReviews;
	
	
	public Book getBook() {
		return book;
	}


	public void setBook(Book book) {
		this.book = book;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public String getPublisher() {
		return publisher;
	}


	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public List<Review> getListReviews() {
		return listReviews;
	}


	public void setListReviews(List<Review> listReviews) {
		this.listReviews = listReviews;
	}


	@Override
	public String toString() {
		return "BookDetail [ Book = "+ book.toString()+" \n author=" + author + ",\n publisher=" + publisher + ",\n category=" + category + "]";
	}





	
	
	
}
