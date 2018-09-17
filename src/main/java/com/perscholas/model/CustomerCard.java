package com.perscholas.model;

import java.sql.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.format.annotation.DateTimeFormat;

public class CustomerCard {
	@NotNull
	private int cardId;
	
	@Pattern(regexp="^\\d*$", message="Credit card number must be numeric")
	private String cardNumber;
	
	@Pattern(regexp="^[a-zA-Z ]*$", message="Holder name must be alphabet")
	private String holderName;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date expireDate;
	
	@NotNull	
	@Min(1)
	@Max(12)
	private int expireMonth;
	@NotNull
	
	private int expireYear;
	
	
	private int securityCode;
	private int customerId;
	public CustomerCard() {
		super();
		// 
	}
	
	
	public CustomerCard(int cardId, String cardNumber, String holderName, Date expireDate, int expireMonth,
			int expireYear, int securityCode, int customerId) {
		super();
		this.cardId = cardId;
		this.cardNumber = cardNumber;
		this.holderName = holderName;
		this.expireDate = expireDate;
		this.expireMonth = expireMonth;
		this.expireYear = expireYear;
		this.securityCode = securityCode;
		this.customerId = customerId;
	}


	public int getExpireMonth() {
		return expireMonth;
	}


	public void setExpireMonth(int expireMonth) {
		this.expireMonth = expireMonth;
	}


	public int getExpireYear() {
		return expireYear;
	}


	public void setExpireYear(int expireYear) {
		this.expireYear = expireYear;
	}


	public int getCardId() {
		return cardId;
	}
	public void setCardId(int cardid) {
		this.cardId = cardid;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getHolderName() {
		return holderName;
	}
	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public int getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(int securityCode) {
		this.securityCode = securityCode;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String get4LastDigits() {
		return this.getCardNumber().substring(this.getCardNumber().length()-4);
	}
	
	@Override
	public String toString() {
		return "CustomerCard [cardId=" + cardId + ", cardNumber=" + cardNumber + ", holderName=" + holderName
				+ ", expireDate=" + expireDate + ", expireMonth=" + expireMonth + ", expireYear=" + expireYear
				+ ", securityCode=" + securityCode + ", customerId=" + customerId + "]";
	}


	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
		
	}
}
