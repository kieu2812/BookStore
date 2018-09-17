package com.perscholas.dao;

import java.sql.SQLException;
import java.util.List;

import com.perscholas.exception.OutOfStockException;
import com.perscholas.model.OrderDetail;

public interface OrderDetailDAO {

	enum SQL{
		INSERT_ORDER_DETAIL("INSERT INTO ORDER_DETAIL( ORDERID, BOOKID, QUANTITY,UNIT_PRICE, SHIPPING_DATE, EXPECT_ARRIVE) " + 
				" VALUES(?,?,?,?,?,?)"),
		GET_ALL_ORDER_DETAIL_BY_ORDERID("SELECT ID, ORDERID, BOOKID, QUANTITY, UNIT_PRICE, SHIPPING_DATE, EXPECT_ARRIVE FROM ORDER_DETAIL WHERE ORDERID= ?");
		private final String query;
		private SQL(String s){
			this.query= s;
			
		}
		public String getQuery() {
			return this.query;
		}
	}
	
	public List<OrderDetail> getAllOrderDetailByOrderId(int orderId);
	public int insertOrderDetail(OrderDetail orderDetail) throws OutOfStockException, SQLException;
}
