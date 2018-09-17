package com.perscholas.dao;

import java.sql.SQLException;
import java.util.List;

import com.perscholas.model.ShippingAddress;

public interface ShippingAddressDAO {
	enum SQL{
		GET_ALL_SHIPPING_ADDRESS("SELECT id, address, city, state, zipcode FROM SHIPPING_ADDRESS"),
		GET_SHIPPING_ADDRESS_BY_ID("SELECT id, address, city, state, zipcode FROM SHIPPING_ADDRESS WHERE ID=?"),
		GET_SHIPPING_ADDRESS_BY_CUST_ID("SELECT id, address, city, state, zipcode FROM SHIPPING_ADDRESS WHERE CUSTID=?"),
		INSERT_SHIPPING_ADDRESS("INSERT INTO SHIPPING_ADDRESS(address, city, state, zipcode)" + 
						" VALUES(?,?,?,?)"),
		UPDATE_SHIPPING_ADDRESS("UPDATE SHIPPING_ADDRESS SET ADDRESS=?, CITY=?, STATE=?, ZIPCODE=? WHERE ID=?"),
		DELETE_SHIPPING_ADDRESS("DELETE FROM SHIPPING_ADDRESS WHERE ID=?");
		
		private final String query;
		private SQL(String s){
			this.query= s;
			
		}
		public String getQuery() {
			return this.query;
		}
		
	}
	public List<ShippingAddress> getAllByCustId(int custId);
	public ShippingAddress getShipping_AddressById(int id);
	public int insertInTransaction(ShippingAddress shipping_Address) throws SQLException;
	public int insertShipping_Address(ShippingAddress shipping_Address) throws SQLException;
	public boolean updateShipping_Address(ShippingAddress shipping_Address);
	public boolean deleteShipping_Address(ShippingAddress shipping_Address);
}
