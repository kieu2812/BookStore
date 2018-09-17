package com.perscholas.services;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.perscholas.dao.AbstractDAO;
import com.perscholas.dao.OrdersDAO;
import com.perscholas.model.Orders;

@Service

public class OrdersService  extends AbstractDAO implements OrdersDAO{
	static Logger log = Logger.getLogger(OrdersService.class);

	@Override
	public List<Orders> getAllOrdersByCustId(int customerid) {
		List<Orders> list= new ArrayList<>();
		PreparedStatement ps= null;
		ResultSet rs =null;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_ALL_ORDER_BY_CUSTID.getQuery());
			ps.setInt(1,  customerid);
			/*select id, customerid, createdate, shippingid, cardid from ORDERS where customerid= ?
			 * */
			rs= ps.executeQuery();
			while(rs.next()) {
				Orders temp = new Orders();
				temp.setId(rs.getInt(1));
				temp.setCustomerId(rs.getInt(2));
				temp.setCreateDate(rs.getDate(3).toLocalDate());;
				temp.setShippingId(rs.getInt(4));
				temp.setCardId(rs.getInt(5));
			
				list.add(temp);
			}
			
		} catch (SQLException e) {

			log.error(String.format("Error at getAllAuthors %s" , e.getMessage()));
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at getAllAuthors %s" , e.getMessage()));
			}
			

		}
		
		
		return list;
	}

	@Override
	public List<Orders> getAllOrdersByCreateDate(LocalDate createDate) {
		List<Orders> list= new ArrayList<>();
		PreparedStatement ps= null;
		ResultSet rs =null;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_ALL_ORDERS_BY_CREATEDATE.getQuery());
			ps.setDate(1, Date.valueOf(createDate));
			/*select id, customerid, createdate, shippingid, cardid from ORDERS where customerid= ?
			 * */
			rs= ps.executeQuery();
			while(rs.next()) {
				Orders temp = new Orders();
				temp.setId(rs.getInt(1));
				temp.setCustomerId(rs.getInt(2));
				temp.setCreateDate(rs.getDate(3).toLocalDate());;
				temp.setShippingId(rs.getInt(4));
				temp.setCardId(rs.getInt(5));
			
				list.add(temp);
			}
			
		} catch (SQLException e) {

			log.error(String.format("Error at getAllAuthors %s" , e.getMessage()));
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at getAllAuthors %s" , e.getMessage()));
			}
			

		}
		
		
		return list;
	}

	@Override
	public int insertOrder(Orders order) throws SQLException{
		PreparedStatement ps= null;
		ResultSet rs= null;
		int keyGenerated= 0;
		try {
			super.getConnection();
			//ps= conn.prepareStatement(SQL.INSERT_ORDER.getQuery(), Statement.RETURN_GENERATED_KEYS);
			ps= conn.prepareStatement(SQL.INSERT_ORDER.getQuery(), new String[] {"ID"});
			ps.setInt(1, order.getCustomerId());
			ps.setDate(2,Date.valueOf(LocalDate.now()));
			ps.setInt(3, order.getShippingId());
			ps.setInt(4, order.getCardId());
			/*INSERT INTO ORDERS(customerid, createdate, shippingid, cardid) VALUES(?,?,?,?) ?
			 * */
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if(rs.next()) {
				keyGenerated =  rs.getInt(1);
			}
		
			
		} finally {
			if(rs!=null)	rs.close();
			if(ps!=null)	ps.close();
			
			//super.closeConnection();
		}
		
		
		return keyGenerated;
	}
	
}
