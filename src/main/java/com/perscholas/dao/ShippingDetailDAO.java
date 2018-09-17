package com.perscholas.dao;

import java.sql.SQLException;
import java.util.List;

import com.perscholas.model.ShippingDetail;

public interface ShippingDetailDAO {
	enum SQL{
	
	
		GET_BY_ORDER_ID("select s.id, s.ORDERDETAILID, s.barcode, s.RETURN_DATE " + 
				" from shipping_detail s inner join order_detail d on s.ORDERDETAILID=d.ID " + 
				"  inner join orders o on o.ID= d.ORDERID " + 
				"  where o.ID=?" ),
		INSERT_BY_ORDER_ID("{ CALL AddShipDetailByOrderId(?)}"),
		INSERT_BY_ORDER_DETAIL_ID("{ CALL AddShipDetailByOrderDetailId(?)}");
		
		private final String query;
		private SQL(String s){
			this.query= s;
			
		}
		public String getQuery() {
			return this.query;
		}
		
	}
	public List<ShippingDetail> getAllByOrderId(int orderId);
	public boolean insertByOrderId(int orderId);
	public boolean insertByOrderDetailId(int orderDetailId) throws SQLException;
}
