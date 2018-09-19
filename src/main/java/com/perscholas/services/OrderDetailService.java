package com.perscholas.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		//Statement st= null;
		ResultSet rs =null;
		int id=0;
		try {

			
			Book bookInStock =  bookDAO.getBookByIdUseInTransaction(orderDetail.getBookid());
			
			log.info("Insert Order Detail with bookId = " + orderDetail.getBookid() + "Book In Stock= " + bookInStock);
			log.info("ORDER DETAIL INFO " + orderDetail);
			if(bookInStock.getQtyInStock()>=orderDetail.getQuantity()) {
				log.info("Quantity in stock >= order Quantity. Process insert Order Detail");
				super.getConnection();
				ps= conn.prepareStatement(SQL.INSERT_ORDER_DETAIL.getQuery(), new String[] {"ID"});
				/*INSERT INTO ORDER_DETōAIL( ORDERID, BOOKID, QUANTITY,UNIT_PRICE, SHIPPING_DATE, EXPECT_ARRIVE)
				 * */
				
				String query= "INSERT INTO ORDER_DETAIL( ORDERID, BOOKID, QUANTITY,UNIT_PRICE, SHIPPING_DATE, EXPECT_ARRIVE) " + 
						" VALUES("+ orderDetail.getOrderId()+","
						+ orderDetail.getBookid()+"," 
						+orderDetail.getQuantity() +","
						+ orderDetail.getUnit_price() +","
					+" trunc(sysdate), trunc(sysdate+3))";

				
				log.info("Query= " +query);
				if(conn!=null) {
					log.error("Call create statement");
					//st =  conn.createStatement();
				}
				else {
					log.error("Can't get connection in insertOrderDetail");
				}
				log.info("before execute");

				//st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
				log.info("Insert Order Detail with orderid="+ orderDetail.getOrderId() +"bookid= " +orderDetail.getBookid()+ " quantity= "+ orderDetail.getQuantity()+
						"Unit_price= "+ orderDetail.getUnit_price());
				ps.setInt(1,  orderDetail.getOrderId());
				ps.setInt(2, orderDetail.getBookid());
				ps.setInt(3, orderDetail.getQuantity());
				ps.setDouble(4, orderDetail.getUnit_price());
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();
				//rs= st.getGeneratedKeys();
				if(rs.next()) {
					id= rs.getInt(1);
				}else {
					log.error("Error at insertOrderDetail because try to execute this query "+ query);
					throw new SQLException("Some error occurs in insertOrderDetail" );
				}
				
			}else {
				log.error("Error at insertOrderDetail because Out of stock of book Name=" + bookInStock.getName());
				throw new OutOfStockException("Out of Stock", bookInStock.getName(), bookInStock.getQtyInStock(), orderDetail.getQuantity());
			}
		}finally {
			if(rs!=null)	rs.close();
			if(ps!=null)	ps.close();
			//if(st!=null)	st.close();
		}
		return id;	
	}
	
}
