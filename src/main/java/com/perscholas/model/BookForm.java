package com.perscholas.model;

import org.springframework.web.multipart.MultipartFile;

public class BookForm {
	private Book book;
	private double invoicePrice;
	private MultipartFile file;
	private String action;
	
	public BookForm() {}

	public BookForm(String action) {
		this.action = action;
	}
	
	public BookForm(Book book, MultipartFile file, int invoicePrice) {
		super();
		this.book = book;
		this.file = file;
		this.invoicePrice = invoicePrice;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public double getInvoicePrice() {
		return invoicePrice;
	}

	public void setInvoicePrice(double invoicePrice) {
		this.invoicePrice = invoicePrice;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	
}
