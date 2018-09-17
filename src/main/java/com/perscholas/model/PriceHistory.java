package com.perscholas.model;

import java.time.LocalDate;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class PriceHistory {
	private int bookid;
	private LocalDate startDate;
	private LocalDate endDate;
	private double invoicePrice;
	private double sellPrice;
	private LocalDate createDate;
	
	public PriceHistory() {}

	public PriceHistory(int bookid, LocalDate startDate, LocalDate endDate, double invoicePrice, double sellPrice,
			LocalDate createDate) {
		super();
		this.bookid = bookid;
		this.startDate = startDate;
		this.endDate = endDate;
		this.invoicePrice = invoicePrice;
		this.sellPrice = sellPrice;
		this.createDate = createDate;
	}

	
	public int getBookid() {
		return bookid;
	}

	public void setBookid(int bookid) {
		this.bookid = bookid;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public double getInvoicePrice() {
		return invoicePrice;
	}

	public void setInvoicePrice(double invoicePrice) {
		this.invoicePrice = invoicePrice;
	}

	public double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "Price_History [bookid=" + bookid + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", invoicePrice=" + invoicePrice + ", sellPrice=" + sellPrice + ", createDate=" + createDate + "]";
	}
	
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
		
	}
}
