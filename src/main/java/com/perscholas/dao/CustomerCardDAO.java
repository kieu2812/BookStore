package com.perscholas.dao;

import java.sql.SQLException;
import java.util.List;

import com.perscholas.model.CustomerCard;

public interface CustomerCardDAO {
	enum SQL{
		GET_ALL_CARDS("SELECT CARDID, CARDNUMBER, HOLDERNAME, EXPIREDATE, SECURITYCODE, CUSTOMERID,   extract(month from EXPIREDATE) as expireMonth, extract(year from expiredate) as expireYear   FROM CUSTOMER_CARD"),
		GET_CARDS_BY_CUSTID("SELECT CARDID, CARDNUMBER, HOLDERNAME, EXPIREDATE, SECURITYCODE, CUSTOMERID,   extract(month from EXPIREDATE) as expireMonth, extract(year from expiredate) as expireYear  FROM CUSTOMER_CARD WHERE CUSTOMERID=?"),
		FINDCARD("SELECT CARDID, CARDNUMBER, HOLDERNAME, EXPIREDATE, SECURITYCODE, CUSTOMERID,   extract(month from EXPIREDATE) as expireMonth, extract(year from expiredate) as expireYear  FROM CUSTOMER_CARD WHERE CARDNUMBER=? AND EXPIREDATE=? AND SECURITYCODE=?"),
		GET_CARD_BY_CARTID("SELECT CARDID, CARDNUMBER, HOLDERNAME, EXPIREDATE, SECURITYCODE, CUSTOMERID,   extract(month from EXPIREDATE) as expireMonth, extract(year from expiredate) as expireYear  FROM CUSTOMER_CARD WHERE CARDID=?"),
		INSERT_CARD("INSERT INTO CUSTOMER_CARD( CARDNUMBER, HOLDERNAME, EXPIREDATE, SECURITYCODE, CUSTOMERID) VALUES(?,?,?,?,?)"),
		UPDATE_CARD("UPDATE CUSTOMER_CARD SET CARDNUMBER=? , HOLDERNAME=?, EXPIREDATE=?, SECURITYCODE=?, CUSTOMERID=? WHERE CARDID=?"),
		DELETE_CARD("DELETE FROM CUSTOMER_CARD WHERE CARDID=?");
		
		private final String query;
		private SQL(String s){
			this.query= s;
			
		}
		public String getQuery() {
			return this.query;
		}
		
		
	}
	public List<CustomerCard> getAllCards();
	public List<CustomerCard> getCardsByCustId(int custid);
	public CustomerCard getCartByCardId(int cardId);
	public CustomerCard findCard(String cardNumber, int securityCode,int expireMonth, int exprieYear);

	public int insertCard(CustomerCard cust) throws SQLException;
	public boolean updateCard(CustomerCard cust);
	public boolean deleteCard(int cardId);
}
