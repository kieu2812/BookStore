package com.perscholas.dao;

import java.sql.SQLException;
import java.util.List;

import com.perscholas.model.PriceHistory;

public interface PriceHistoryDAO {
	enum SQL{
		
		GET_CURRENT_PRICE_BY_BOOKID("SELECT BOOKID, STARTDATE, ENDDATE, INVOICEPRICE, SELLPRICE,CREATEDATE FROM PRICE_HISTORY WHERE ENDDATE IS NULL AND BOOKID=? "),
		GET_HISTORY_PRICE("SELECT BOOKID, STARTDATE, ENDDATE, INVOICEPRICE, SELLPRICE,CREATEDATE FROM PRICE_HISTORY WHERE BOOKID=? order by startdate desc "),
		UPDATE_NEW_PRICE("update price_history set INVOICEPRICE =?, SELLPRICE=? where bookid=? AND ENDDATE IS NULL "),
		INSERT_PRICE("INSERT INTO PRICE_HISTORY(BOOKID, STARTDATE,  INVOICEPRICE, SELLPRICE,CREATEDATE )  VALUES(?,?,?,?,?)"),
		SAVE_PRICE_HISTORY("{CALL SavePriceHistory(?,?,?)}");

		private final String query;
		private SQL(String s){
			this.query= s;
			
		}
		public String getQuery() {
			return this.query;
		}
	}
	//public PriceHistory getCurrentPrice
	public List<PriceHistory> getPriceHistoryById(int bookId);
	public boolean insertUpdatePrice(int bookId,double  invoicePrice,  double sellPrice) throws  SQLException;
	public PriceHistory getCurrentPriceById(int bookId) ;

}
