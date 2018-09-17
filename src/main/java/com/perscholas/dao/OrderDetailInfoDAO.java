package com.perscholas.dao;

import java.util.List;

import com.perscholas.model.OrderDetailInfo;

public interface OrderDetailInfoDAO {
	enum SQL{
		GET_ALL_ORDER_DETAIL_INFO_BY_ORDER_ID("select d.id, d.bookid, b.name, d.QUANTITY, d.UNIT_PRICE, sum(d.quantity* d.UNIT_PRICE) as amount from order_Detail d inner join book b  on d.bookid = b.id  " + 
				"where d.orderId=? " + 
				"group by d.id,d.bookid, b.name, d.QUANTITY, d.unit_price" ); 
				
		
		private final String query;
		private SQL(String s){
			this.query= s;
			
		}
		public String getQuery() {
			return this.query;
		}
	}
	
	
	public List<OrderDetailInfo> getAllOrderDetailInfoByOrderId(int orderId);
	
}
