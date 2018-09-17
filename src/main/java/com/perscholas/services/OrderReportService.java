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
import com.perscholas.dao.OrderReportDAO;
import com.perscholas.model.OrderReport;

@Service
public class OrderReportService extends AbstractDAO implements OrderReportDAO {

	static Logger log = Logger.getLogger(OrderReportService.class);
	@Override
	public List<OrderReport> getAllByMonthAndYear(int month, int year) {
		List<OrderReport> list = new ArrayList<>();
		
		PreparedStatement ps= null;
		ResultSet rs =null;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_ALL_BY_MONTH.getQuery());
			ps.setInt(1,  month);
			ps.setInt(2, year);
			/*"select o.id,  c.FIRST_NAME || ' ' || c.LAST_NAME as CustomerName, " + 
				"				        c.ADDRESS || ' '|| c.city || ' ' || c.STATE || ' '  as CustomerAddress, " + 
				"				              o.CREATEDATE, " + 
				"				            od.amount" + 
				"				 from orders o " + 
				"            inner join customer c on c.ID= o.CUSTOMERID " + 
				"				    inner join (select sum(d.quantity* d.unit_price) as amount , d.ORDERID   " + 
				"				                from  order_detail d  " + 
				"				                group by d.ORDERID " + 
				"				                ) od  " + 
				"				                on od.orderid =  o.id " + 
				"				 where EXTRACT(month FROM o.CREATEDATE)=? and EXTRACT(year from o.CREATEDATE)=?"
			 * */
			rs= ps.executeQuery();
			while(rs.next()) {
				OrderReport orderReport = new OrderReport();
				orderReport.setId(rs.getInt(1));
			
				orderReport.setCustomerName(rs.getString(2));
				orderReport.setCustomerAddress(rs.getString(3));
				orderReport.setCreateDate(rs.getDate(4));
				orderReport.setAmount(rs.getDouble(5));
				orderReport.setInvoice(rs.getDouble(6));
				list.add(orderReport);
			}
			
		} catch (SQLException e) {
			
			log.info("A error occurs in OrderReportService.getAllByMonthAndYear" + e.getMessage() );
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.info("A error occurs in OrderReportService.getAllByMonthAndYear" + e.getMessage() );

			}
			
			super.closeConnection();
		}
		
		
		return list;
	}
	@Override
	public List<OrderReport> getAllByUserName(String email) {
	List<OrderReport> list = new ArrayList<>();
		
		PreparedStatement ps= null;
		ResultSet rs =null;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_ALL_BY_CUSTID.getQuery());

			/*	"select o.id,  c.FIRST_NAME || ' ' || c.LAST_NAME as CustomerName, " + 
				"				        c.ADDRESS || ' '|| c.city || ' ' || c.STATE || ' '  as CustomerAddress, " + 
				"				              o.CREATEDATE, " + 
				"				            od.amount" + 
				"				 from orders o " + 
				"            inner join customer c on c.ID= o.CUSTOMERID " + 
				"				    inner join (select sum(d.quantity* d.unit_price) as amount , d.ORDERID   " + 
				"				                from  order_detail d  " + 
				"				                group by d.ORDERID " + 
				"				                ) od  " + 
				"				                on od.orderid =  o.id " + 
				"				 where c.email=?" +
				 " order by o.CREATEDATE, o.id"	
			 * 
			 * */		
			ps.setString(1, email);
			rs= ps.executeQuery();
			while(rs.next()) {
				OrderReport orderReport = new OrderReport();
				orderReport.setId(rs.getInt(1));
			
				orderReport.setCustomerName(rs.getString(2));
				orderReport.setCustomerAddress(rs.getString(3));
				orderReport.setCreateDate(rs.getDate(4));
				orderReport.setAmount(rs.getDouble(5));
				orderReport.setInvoice(rs.getDouble(6));
				list.add(orderReport);
			}
			
		} catch (SQLException e) {
			
			log.info("A error occurs in OrderReportService.getAllByMonthAndYear "  + e.getMessage());
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.info("A error occurs in OrderReportService.getAllByMonthAndYear "  + e.getMessage());

			}
			
			super.closeConnection();
		}
		return list;
	}
	@Override
	public List<OrderReport> getAllByDate(LocalDate localDate) {
		List<OrderReport> list = new ArrayList<>();
		
		PreparedStatement ps= null;
		ResultSet rs =null;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_ALL_BY_DATE.getQuery());
			ps.setDate(1, Date.valueOf(localDate) );
			
			/*
			 * "select o.id,  c.FIRST_NAME || ' ' || c.LAST_NAME as CustomerName,   " + 
				                "c.ADDRESS || ' '|| c.city || ' ' || c.STATE || ' '  as CustomerAddress,   " + 
				"					   O.Createdate,   " + 
				"  		            od.amount, od.invoice " + 
				"		 from orders o   " + 
				"		            Inner Join Customer C On C.Id= O.Customerid   " + 
				"				    Inner Join ( " + 
				"       			             Select Sum(D.Quantity* D.Unit_Price) As Amount, Sum(P.InvoicePrice) As Invoice, D.Orderid    " + 
				"					             From  Order_Detail D  Inner Join Price_History P On D.Bookid=P.Bookid inner join Orders O on O.id=d.orderid " + 
				"                                where O.Createdate>= P.Startdate And (O.Createdate< P.Enddate Or P.Enddate Is Null) " + 
				"					             group by d.ORDERID  " + 
				"					           ) od    " + 
				"	          On Od.Orderid =  O.Id "	+								
				"		 where  to_char(o.CREATEDATE,'mm/dd/yyyy')= to_char(?,'mm/dd/yyyy')" +
				 " order by o.CREATEDATE, o.id")
				 
			 * */
			rs= ps.executeQuery();
			while(rs.next()) {
				OrderReport orderReport = new OrderReport();
				orderReport.setId(rs.getInt(1));
			
				orderReport.setCustomerName(rs.getString(2));
				orderReport.setCustomerAddress(rs.getString(3));
				orderReport.setCreateDate(rs.getDate(4));
				orderReport.setAmount(rs.getDouble(5));
				orderReport.setInvoice(rs.getDouble(6));
				list.add(orderReport);
			}
			
		} catch (SQLException e) {
			
			log.info("A error occurs in OrderReportService.getAllByDate" + e.getMessage());
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.info("A error occurs in OrderReportService.getAllByDate" + e.getMessage());
			}finally {
			}
			
			super.closeConnection();
		}
		
		
		return list;
	}
	

}
