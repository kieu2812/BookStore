package com.perscholas.model;


import java.sql.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class OrderInfo {
	private int id;
	private String shippingAddress;
	private String cardNumber;
	private String customerName;
	private String customerAddress;
	private Date createDate;
	private double amount;
	private int customerId;
	public OrderInfo() {
		super();
		// 
	}
	
	public OrderInfo(int id, String shippingAddress, String cardNumber, String customerName, String customerAddress,
			Date createDate, double amount, int customerId) {
		super();
		this.id = id;
		this.shippingAddress = shippingAddress;
		this.cardNumber = cardNumber;
		this.customerName = customerName;
		this.customerAddress = customerAddress;
		this.createDate = createDate;
		this.amount = amount;
		this.customerId = customerId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
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


	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "OrderInfo [id=" + id + ", shippingAddress=" + shippingAddress + ", cardNumber=" + cardNumber
				+ ", customerName=" + customerName + ", customerAddress=" + customerAddress + ", createDate="
				+ createDate + ", amount=" + amount + ", customerId=" + customerId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((cardNumber == null) ? 0 : cardNumber.hashCode());
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((customerAddress == null) ? 0 : customerAddress.hashCode());
		result = prime * result + customerId;
		result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
		result = prime * result + id;
		result = prime * result + ((shippingAddress == null) ? 0 : shippingAddress.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
}
