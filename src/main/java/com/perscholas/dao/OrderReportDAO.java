package com.perscholas.dao;

import java.time.LocalDate;
import java.util.List;

import com.perscholas.model.OrderReport;

public interface OrderReportDAO {
	enum SQL{
		GET_ALL_BY_MONTH("select o.id,  c.FIRST_NAME || ' ' || c.LAST_NAME as CustomerName,   " + 
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
				" 		where EXTRACT(month FROM o.CREATEDATE)=? and EXTRACT(year from o.CREATEDATE)=?" +
				"		order by o.CREATEDATE, o.id"),
		GET_ALL_BY_DATE("select o.id,  c.FIRST_NAME || ' ' || c.LAST_NAME as CustomerName,   " + 
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
				 " order by o.CREATEDATE, o.id"),

		GET_ALL_BY_CUSTID("select o.id,  c.FIRST_NAME || ' ' || c.LAST_NAME as CustomerName, " + 
				"				        c.ADDRESS || ' '|| c.city || ' ' || c.STATE || ' '  as CustomerAddress, " + 
				"				        o.CREATEDATE, " + 
				"				        od.amount,od.invoice " + 
				"		 from orders o   " + 
				"		            Inner Join Customer C On C.Id= O.Customerid   " + 
				"				    Inner Join ( " + 
				"       			             Select Sum(D.Quantity* D.Unit_Price) As Amount, Sum(P.InvoicePrice) As Invoice, D.Orderid    " + 
				"					             From  Order_Detail D  Inner Join Price_History P On D.Bookid=P.Bookid inner join Orders O on O.id=d.orderid " + 
				"                                where O.Createdate>= P.Startdate And (O.Createdate< P.Enddate Or P.Enddate Is Null) " + 
				"					             group by d.ORDERID  " + 
				"					           ) od    " + 
				"	          On Od.Orderid =  O.Id "	+								
				"				 where c.email=?" +
				 " order by o.CREATEDATE, o.id");
		
		
		private final String query;
		private SQL(String s){
			this.query= s;
			
		}
		public String getQuery() {
			return this.query;
		}
	}

	public List<OrderReport> getAllByMonthAndYear(int month, int year);
	public List<OrderReport> getAllByUserName(String email);
	public List<OrderReport> getAllByDate(LocalDate localDate);
}
