package com.perscholas.model;

import java.sql.Date;

/**
 * 
 * This class use for Order Report Monthly and View Your Order  
 *
 */
public class OrderReport {
	private int id;
	private String customerName;
	private String customerAddress;
	private Date createDate;
	private double amount;
	private double invoice;
	
	public OrderReport() {}
	
	public OrderReport(int id, String customerName, String customerAddress, Date createDate, double amount, double invoice) {
		super();
		this.id = id;
		this.customerName = customerName;
		this.customerAddress = customerAddress;
		this.createDate = createDate;
		this.amount = amount;
		this.invoice= invoice;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getInvoice() {
		return invoice;
	}

	public void setInvoice(double invoice) {
		this.invoice = invoice;
	}
	

	
}
