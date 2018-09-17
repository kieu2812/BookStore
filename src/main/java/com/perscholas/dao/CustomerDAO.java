package com.perscholas.dao;


import java.util.List;

import com.perscholas.model.Customer;

public interface CustomerDAO {
	enum SQL{
		GET_ALL_CUSTOMERS("SELECT id, first_name, last_name, address, city, state, email, zipcode FROM CUSTOMER"),
		GET_CUSTOMER_BY_ID("SELECT id, first_name, last_name, address, city, state, email, zipcode FROM CUSTOMER WHERE ID=?"),
		GET_CUSTOMER_BY_EMAIL("SELECT id, first_name, last_name, address, city, state, email, zipcode FROM CUSTOMER WHERE EMAIL=?"),
		INSERT_CUSTOMER("INSERT INTO CUSTOMER(first_name, last_name, address, city, state, email, zipcode)" + 
						" VALUES(?,?,?,?,?,?,?)"),
		UPDATE_CUSTOMER("UPDATE CUSTOMER SET FIRST_NAME=? LAST_NAME=?,ADDRESS=?, CITY=?, STATE=?, EMAIL=?, zipcode=? WHERE ID=?"),
		DELETE_CUSTOMER("DELETE FROM CUSTOMER WHERE ID=?");
		
		private final String query;
		private SQL(String s){
			this.query= s;
			
		}
		public String getQuery() {
			return this.query;
		}
		
	}
	public List<Customer> getAllCustomers();
	public Customer getCustomerById(int id);
	public Customer getCustomerByEmail(String email);
	public int  insertCustomer(Customer cust);
	public boolean updateCustomer(Customer cust);
	public boolean deleteCustomer(Customer cust);
}
