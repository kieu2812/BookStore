package com.perscholas.services;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perscholas.dao.AbstractDAO;
import com.perscholas.dao.BookDAO;
import com.perscholas.dao.OrderDetailDAO;
import com.perscholas.exception.OutOfStockException;
import com.perscholas.model.Book;
import com.perscholas.model.OrderDetail;

@Service

public class OrderDetailService extends AbstractDAO implements OrderDetailDAO{
	static Logger log = Logger.getLogger(OrderDetailService.class);

	@Autowired
	BookDAO bookDAO ;
	@Override
	public List<OrderDetail> getAllOrderDetailByOrderId(int orderId) {
		List<OrderDetail> list= new ArrayList<>();
		PreparedStatement ps= null;
		ResultSet rs =null;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_ALL_ORDER_DETAIL_BY_ORDERID.getQuery());
			ps.setInt(1,  orderId);
			/*SELECT ID, ORDERID, BOOKID, QUANTITY, UNIT_PRICE, SHIPPING_DATE, EXPECT_ARRIVE FROM ORDER_DETAIL WHERE ORDERID= ?
			 * */
			rs= ps.executeQuery();
			while(rs.next()) {
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setId(rs.getInt(1));
				orderDetail.setOrderId(rs.getInt(2));
				orderDetail.setBookid(rs.getInt(3));
				orderDetail.setQuantity(rs.getInt(4));
				orderDetail.setUnit_price(rs.getInt(5));
				orderDetail.setShipping_date(rs.getDate(6).toLocalDate());
				orderDetail.setExpect_date(rs.getDate(7).toLocalDate());
				list.add(orderDetail);
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
			
			super.closeConnection();
		}
		
		
		return list;
	}

	@Override
	public int insertOrderDetail(OrderDetail orderDetail) throws OutOfStockException, SQLException {
		
		PreparedStatement ps= null;
		ResultSet rs =null;
		int id=0;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.INSERT_ORDER_DETAIL.getQuery(), new String[] {"ID"});
			/*INSERT INTO ORDER_DETAIL( ORDERID, BOOKID, QUANTITY,UNIT_PRICE, SHIPPING_DATE, EXPECT_ARRIVE)
			 * */
			Book bookInStock =  bookDAO.getBookById(orderDetail.getBookid());
			
			if(bookInStock.getQtyInStock()>=orderDetail.getQuantity()) {
				ps.setInt(1,  orderDetail.getOrderId());
				ps.setInt(2, orderDetail.getBookid());
				ps.setInt(3, orderDetail.getQuantity());
				ps.setDouble(4, orderDetail.getUnit_price());
				ps.setDate(5, Date.valueOf(LocalDate.now()));
				ps.setDate(6, Date.valueOf(LocalDate.now().plusDays(3)));
				
				
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();
				if(rs.next()) {
					id= rs.getInt(1);
				}
			}else {
				throw new OutOfStockException("Out of Stock", bookInStock.getName(), bookInStock.getQtyInStock(), orderDetail.getQuantity());
			}
		}finally {
			if(rs!=null)	rs.close();
			if(ps!=null)	ps.close();
			
		}
		return id;	
	}
	
}
