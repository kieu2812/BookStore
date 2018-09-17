package com.perscholas.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.perscholas.model.Orders;

public interface OrdersDAO {
	enum SQL{
		GET_ALL_ORDER_BY_CUSTID("select id, customerid, createdate, shippingid, cardid from ORDERS where customerid=?"),
		INSERT_ORDER("INSERT INTO ORDERS(customerid, createdate, shippingid, cardid) VALUES(?,?,?,?)"),
		GET_ALL_ORDERS_BY_CREATEDATE("select id, customerid, createdate, shippingid, cardid from ORDERS where createdate=?");
		
		private final String query;
		private SQL(String s){
			this.query= s;
			
		}
		public String getQuery() {
			return this.query;
		}
		
	}
	public List<Orders> getAllOrdersByCustId(int customerid);
	
	public List<Orders> getAllOrdersByCreateDate(LocalDate createDate);
	public int insertOrder(Orders order) throws SQLException;

	
}
