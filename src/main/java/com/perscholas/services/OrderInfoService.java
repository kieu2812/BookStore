package com.perscholas.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.perscholas.dao.AbstractDAO;
import com.perscholas.dao.OrderInfoDAO;
import com.perscholas.model.OrderInfo;

@Service
public class OrderInfoService extends AbstractDAO implements OrderInfoDAO {
	static Logger log = Logger.getLogger(OrderInfoService.class);

	@Override
	public OrderInfo getOrderInfoById(int orderId) {
		OrderInfo orderInfo = null;
		PreparedStatement ps= null;
		ResultSet rs =null;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_ORDERINFO_BY_ID.getQuery());
			ps.setInt(1,  orderId);
			/*select o.id as order_id, cc.CARDNUMBER, c.FIRST_NAME || ' ' || c.LAST_NAME as CustomerName," + 
				"       c.ADDRESS || ' '|| c.city || ' ' || c.STATE || ' ' || c.zipcode as CustomerAddress," + 
				"				case  " + 
				"          when s.id is null then  c.ADDRESS || ' '|| c.city || ' ' || c.STATE || ' ' || c.zipcode" + 
				"          when s.id is not null then  s.ADDRESS || ' '|| s.city || ' ' || s.STATE || ' ' || s.zipcode " + 
				"        end as ShippingAddress," + 
				"				o.CREATEDATE," + 
				"				od.amount, c.id as custID " + 
				"				from orders o " + 
				"				inner join CUSTOMER_CARD cc on cc.CARDID= o.CARDID" + 
				"				inner join customer c on c.ID= o.CUSTOMERID " + 
				"        left join SHIPPING_ADDRESS s on s.ID= o.ID" + 
				"				inner join (select sum(d.quantity* d.unit_price) as amount , d.ORDERID " + 
				"				from  order_detail d " + 
				"				group by d.ORDERID" + 
				"				) od " + 
				"				on od.orderid =  o.id" + 
				"			 where o.ID = ?
			 * */
			rs= ps.executeQuery();
			if(rs.next()) {
				
				orderInfo = new OrderInfo();
				orderInfo.setId(rs.getInt(1));
				orderInfo.setCardNumber(rs.getString(2));
				orderInfo.setCustomerName(rs.getString(3));
				orderInfo.setCustomerAddress(rs.getString(4));
				orderInfo.setShippingAddress(rs.getString(5));
				orderInfo.setCreateDate(rs.getDate(6));
				orderInfo.setAmount(rs.getDouble(7));
				orderInfo.setCustomerId(rs.getInt(8));
			}
			
		} catch (SQLException e) {

			log.error(String.format("Error at getOrderInfoById %s" , e.getMessage()));
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at getOrderInfoById %s" , e.getMessage()));

			}
			
			super.closeConnection();
		}
		
		
		return orderInfo;
	}

	@Override
	public List<OrderInfo> getAllOrderInfoByCustId(int customerId) {
		List<OrderInfo> list = new ArrayList<>();
		OrderInfo orderInfo = null;
		PreparedStatement ps= null;
		ResultSet rs =null;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_ALL_ORDERINFO_BY_CUSTID.getQuery());
			ps.setInt(1,  customerId);
			/*"select o.id as order_id, cc.CARDNUMBER, c.FIRST_NAME || ' ' || c.LAST_NAME as CustomerName," + 
				"       c.ADDRESS || ' '|| c.city || ' ' || c.STATE || ' ' || c.zipcode as CustomerAddress," + 
				"				case  " + 
				"          when s.id is null then  c.ADDRESS || ' '|| c.city || ' ' || c.STATE || ' ' || c.zipcode" + 
				"          when s.id is not null then  s.ADDRESS || ' '|| s.city || ' ' || s.STATE || ' ' || s.zipcode " + 
				"        end as ShippingAddress," + 
				"				o.CREATEDATE," + 
				"				od.amount, c.id as custID " + 
				"				from orders o " + 
				"				inner join CUSTOMER_CARD cc on cc.CARDID= o.CARDID" + 
				"				inner join customer c on c.ID= o.CUSTOMERID " + 
				"        left join SHIPPING_ADDRESS s on s.ID= o.ID" + 
				"				inner join (select sum(d.quantity* d.unit_price) as amount , d.ORDERID " + 
				"				from  order_detail d " + 
				"				group by d.ORDERID" + 
				"				) od " + 
				"				on od.orderid =  o.id" + 
				"			 where o.CUSTOMERID = ?"
			 * */
			rs= ps.executeQuery();
			while(rs.next()) {
				orderInfo = new OrderInfo();
				orderInfo.setId(rs.getInt(1));
				orderInfo.setCardNumber(rs.getString(2));
				orderInfo.setCustomerName(rs.getString(3));
				orderInfo.setCustomerAddress(rs.getString(4));
				orderInfo.setShippingAddress(rs.getString(5));
				orderInfo.setCreateDate(rs.getDate(6));
				orderInfo.setAmount(rs.getDouble(7));
				orderInfo.setCustomerId(rs.getInt(8));
				list.add(orderInfo);
			}
			
		} catch (SQLException e) {

			log.error(String.format("Error at getAllOrderInfoByCustId %s" , e.getMessage()));
			
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at getAllOrderInfoByCustId %s" , e.getMessage()));

			}
			
			super.closeConnection();
		}
		
		
		return list;
	}

	@Override
	public List<OrderInfo> getAllOrderInfoByEmail(String email) {
		List<OrderInfo> list = new ArrayList<>();
		OrderInfo orderInfo = null;
		PreparedStatement ps= null;
		ResultSet rs =null;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_ALL_ORDERINFO_BY_CUSTID.getQuery());
			ps.setString(1,  email);
			/*"select o.id as order_id, cc.CARDNUMBER, c.FIRST_NAME || ' ' || c.LAST_NAME as CustomerName," + 
				"       c.ADDRESS || ' '|| c.city || ' ' || c.STATE || ' ' || c.zipcode as CustomerAddress," + 
				"				case  " + 
				"          when s.id is null then  c.ADDRESS || ' '|| c.city || ' ' || c.STATE || ' ' || c.zipcode" + 
				"          when s.id is not null then  s.ADDRESS || ' '|| s.city || ' ' || s.STATE || ' ' || s.zipcode " + 
				"        end as ShippingAddress," + 
				"				o.CREATEDATE," + 
				"				od.amount, c.id as custID " + 
				"				from orders o " + 
				"				inner join CUSTOMER_CARD cc on cc.CARDID= o.CARDID" + 
				"				inner join customer c on c.ID= o.CUSTOMERID " + 
				"        left join SHIPPING_ADDRESS s on s.ID= o.ID" + 
				"				inner join (select sum(d.quantity* d.unit_price) as amount , d.ORDERID " + 
				"				from  order_detail d " + 
				"				group by d.ORDERID" + 
				"				) od " + 
				"				on od.orderid =  o.id" + 
				"			 where c.email = ?"
			 * */
			rs= ps.executeQuery();
			while(rs.next()) {
				orderInfo = new OrderInfo();
				orderInfo.setId(rs.getInt(1));
				orderInfo.setCardNumber(rs.getString(2));
				orderInfo.setCustomerName(rs.getString(3));
				orderInfo.setCustomerAddress(rs.getString(4));
				orderInfo.setShippingAddress(rs.getString(5));
				orderInfo.setCreateDate(rs.getDate(6));
				orderInfo.setAmount(rs.getDouble(7));
				orderInfo.setCustomerId(rs.getInt(8));
				list.add(orderInfo);
			}
			
		} catch (SQLException e) {

			log.error(String.format("Error at getAllOrderInfoByEmail %s" , e.getMessage()));

		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at getAllOrderInfoByEmail %s" , e.getMessage()));

			}
			
			super.closeConnection();
		}
		
		
		return list;
	}

	@Override
	public boolean checkCustBuyBook(int customerId, int bookId) {
		PreparedStatement ps= null;
		ResultSet rs=null;
		boolean isBought=false;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.CHECK_BUY_BOOK_BY_CUST_ID.getQuery());
			ps.setInt(1,  customerId);
			ps.setInt(2,  bookId);
			
			rs=   ps.executeQuery();
			if(rs.next()) {
				if(rs.getInt(1)>0)
					isBought=true;
			}
			
		} catch (SQLException e) {

			log.error(String.format("Error at checkCustBuyBook %s" , e.getMessage()));

		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at checkCustBuyBook %s" , e.getMessage()));

			}
			

		}
		
		return isBought;
	}

}
