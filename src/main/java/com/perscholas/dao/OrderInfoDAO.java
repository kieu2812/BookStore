package com.perscholas.dao;

import java.util.List;

import com.perscholas.model.OrderInfo;

public interface OrderInfoDAO {
	enum SQL{
		GET_ALL_ORDERINFO_BY_CUSTID("select o.id as order_id, cc.CARDNUMBER, c.FIRST_NAME || ' ' || c.LAST_NAME as CustomerName," + 
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
				"			 where o.CUSTOMERID = ?"),
		GET_ALL_ORDERINFO_BY_EMAIL("select o.id as order_id, cc.CARDNUMBER, c.FIRST_NAME || ' ' || c.LAST_NAME as CustomerName," + 
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
				"			 where c.email = ?"),
		GET_ORDERINFO_BY_ID( 
				"select o.id as order_id, cc.CARDNUMBER, c.FIRST_NAME || ' ' || c.LAST_NAME as CustomerName, " + 
				"				       c.ADDRESS || ' '|| c.city || ' ' || c.STATE || ' ' || c.zipcode as CustomerAddress, " + 
				"								Case   " + 
				"				          When o.Shippingid =0  Then  C.Address || ' '|| C.City || ' ' || C.State || ' ' || C.Zipcode " + 
				"				          when o.shippingid<>0 then  s.ADDRESS || ' '|| s.city || ' ' || s.STATE || ' ' || s.zipcode  " + 
				"				        end as ShippingAddress, " + 
				"								o.CREATEDATE, " + 
				"								od.amount, c.id as custID  " + 
				"								from orders o  " + 
				"								inner join CUSTOMER_CARD cc on cc.CARDID= o.CARDID " + 
				"								inner join customer c on c.ID= o.CUSTOMERID  " + 
				"				        inner join SHIPPING_ADDRESS s on s.ID= o.ID " + 
				"								inner join (select sum(d.quantity* d.unit_price) as amount , d.ORDERID  " + 
				"								from  order_detail d  " + 
				"								group by d.ORDERID " + 
				"								) od  " + 
				"								On Od.Orderid =  O.Id " + 
				"  				 where o.ID = ?"),
		CHECK_BUY_BOOK_BY_CUST_ID("select count(*) from orders o inner join order_detail d on o.id=d.ORDERID " + 
				"where o.CUSTOMERID=? and d.BOOKID=?");
		
		private final String query;
		private SQL(String s){
			this.query= s;
			
		}
		public String getQuery() {
			return this.query;
		}
	}
	public OrderInfo getOrderInfoById(int orderId);
	public List<OrderInfo> getAllOrderInfoByCustId(int customerId);
	public List<OrderInfo> getAllOrderInfoByEmail(String email);
	public boolean checkCustBuyBook(int customerId, int bookId);
}
