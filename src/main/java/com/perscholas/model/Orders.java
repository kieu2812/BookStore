package com.perscholas.model;

import java.time.LocalDate;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class Orders {
	private int id;
	private int customerId;
	private int shippingId;
	private int cardId;
	private String customerName;
	private String customerAddress;
	private String customerPhone;
	private LocalDate createDate;
	
	public Orders() {}

	
	public Orders(int id, int customerId, int shippingId, int cardId, String customerName, String customerAddress,
			String customerPhone, LocalDate createDate) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.shippingId = shippingId;
		this.cardId = cardId;
		this.customerName = customerName;
		this.customerAddress = customerAddress;
		this.customerPhone = customerPhone;
		this.createDate = createDate;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getCustomerId() {
		return customerId;
	}


	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}


	public int getShippingId() {
		return shippingId;
	}


	public void setShippingId(int shippingId) {
		this.shippingId = shippingId;
	}


	public int getCardId() {
		return cardId;
	}


	public void setCardId(int cardId) {
		this.cardId = cardId;
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


	public String getCustomerPhone() {
		return customerPhone;
	}


	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}


	public LocalDate getCreateDate() {
		return createDate;
	}


	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}


	@Override
	public String toString() {
		return "Orders [id=" + id + ", customerId=" + customerId + ", shippingId=" + shippingId + ", cardId=" + cardId
				+ ", customerName=" + customerName + ", customerAddress=" + customerAddress + ", customerPhone="
				+ customerPhone + ", createDate=" + createDate + "]";
	}


	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
		
	}
	
}
