package com.perscholas.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.perscholas.dao.AbstractDAO;
import com.perscholas.dao.OrderDetailInfoDAO;
import com.perscholas.model.OrderDetailInfo;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service

public class OrderDetailInfoService extends AbstractDAO implements OrderDetailInfoDAO{
	
	static Logger log = Logger.getLogger(OrderDetailInfoService.class);


	@Override
	public List<OrderDetailInfo> getAllOrderDetailInfoByOrderId(int orderId) {
		List<OrderDetailInfo> list= new ArrayList<>();
		PreparedStatement ps= null;
		ResultSet rs =null;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_ALL_ORDER_DETAIL_INFO_BY_ORDER_ID.getQuery());
			ps.setInt(1,  orderId);
			/*"select d.id, d.bookid, b.name, d.QUANTITY, d.UNIT_PRICE, sum(d.quantity* d.UNIT_PRICE) as amount from order_Detail d inner join book b  on d.bookid = b.id   
				where d.orderId=2  
				group by d.id,d.bookid, b.name, d.QUANTITY, d.unit_price;
			 * */
			rs= ps.executeQuery();
			while(rs.next()) {
				OrderDetailInfo detailInfo = new OrderDetailInfo();	
				detailInfo.setId(rs.getInt(1));
				detailInfo.setBookid(rs.getInt(2));
				detailInfo.setBookName(rs.getString(3));
				detailInfo.setQuantity(rs.getInt(4));
				detailInfo.setUnit_price(rs.getInt(5));
				detailInfo.setAmount(rs.getDouble(6));

				list.add(detailInfo);
			}
			
		} catch (SQLException e) {
			log.error(String.format("Error at getAllOrderDetailInfoByOrderId %s" , e.getMessage()));

		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at getAllOrderDetailInfoByOrderId %s" , e.getMessage()));

			}
			
			super.closeConnection();
		}
		
		
		return list;
	}
	
}
