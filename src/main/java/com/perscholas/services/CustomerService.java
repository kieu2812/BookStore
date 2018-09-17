package com.perscholas.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.perscholas.dao.AbstractDAO;
import com.perscholas.dao.CustomerDAO;
import com.perscholas.model.Customer;

@Service

public class CustomerService extends AbstractDAO implements CustomerDAO {
	static Logger log = Logger.getLogger(CustomerService.class);

	@Override
	public List<Customer> getAllCustomers() {
		
		List<Customer> customers=null;
		PreparedStatement ps= null;
		ResultSet rs =null;
		
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_ALL_CUSTOMERS.getQuery());
			rs = ps.executeQuery();
			customers =new ArrayList<Customer>();
			while(rs.next()) {
				Customer tempCustomer = new Customer();
				//id, first_name, last_name, address, city, state, email
				tempCustomer.setId(rs.getInt("id"));
				tempCustomer.setFirstName(rs.getString("first_name"));
				tempCustomer.setLastName(rs.getString("last_name"));
				tempCustomer.setAddress(rs.getString("address"));
				tempCustomer.setCity(rs.getString("city"));
				tempCustomer.setState(rs.getString("state"));
				tempCustomer.setEmail(rs.getString("email"));
				tempCustomer.setZipcode(rs.getString("zipcode"));
				
				customers.add(tempCustomer);
			}
		} catch (SQLException e) {
			log.error(String.format("Error at getAllCustomers %s" , e.getMessage()));
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at getAllCustomers %s" , e.getMessage()));
			}
			
			super.closeConnection();
		}
		
		
		return customers;
	}

	@Override
	public Customer getCustomerById(int id) {
		
		
		PreparedStatement ps= null;
		ResultSet rs =null;
		Customer tempCustomer =null;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_CUSTOMER_BY_ID.getQuery());
			ps.setInt(1, id);
			rs = ps.executeQuery();
		
			if(rs.next()) {
				tempCustomer = new Customer();
				//id, first_name, last_name, address, city, state, email
				tempCustomer.setId(rs.getInt("id"));
				tempCustomer.setFirstName(rs.getString("first_name"));
				tempCustomer.setLastName(rs.getString("last_name"));
				tempCustomer.setAddress(rs.getString("address"));
				tempCustomer.setCity(rs.getString("city"));
				tempCustomer.setState(rs.getString("state"));
				tempCustomer.setEmail(rs.getString("email"));
				tempCustomer.setZipcode(rs.getString("zipcode"));

				
			}
		} catch (SQLException e) {
			log.error(String.format("Error at getCustomerById %s" , e.getMessage()));
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at getCustomerById %s" , e.getMessage()));
			}
			
			super.closeConnection();
		}
		
		
		return tempCustomer;
	}

	@Override
	public Customer getCustomerByEmail(String email) {
		Customer customer=null;
		PreparedStatement ps= null;
		ResultSet rs =null;
		
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_CUSTOMER_BY_EMAIL.getQuery());
			ps.setString(1,  email);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				 customer=new Customer();
				//id, first_name, last_name, address, city, state, email
				customer.setId(rs.getInt("id"));
				customer.setFirstName(rs.getString("first_name"));
				customer.setLastName(rs.getString("last_name"));
				customer.setAddress(rs.getString("address"));
				customer.setCity(rs.getString("city"));
				customer.setState(rs.getString("state"));
				customer.setEmail(rs.getString("email"));
				customer.setZipcode(rs.getString("zipcode"));

			}
		} catch (SQLException e) {
			log.error(String.format("Error at getCustomerByEmail %s" , e.getMessage()));
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at getCustomerByEmail %s" , e.getMessage()));
			}
			
			super.closeConnection();
		}
		
		
		return customer;
	}

	@Override
	public int  insertCustomer(Customer cust) {
		
	
		PreparedStatement ps= null;
		ResultSet rs =null;
		int keyGenerated= 0;
		try {
			super.getConnection();
			//ps= conn.prepareStatement(SQL.INSERT_CUSTOMER.getQuery(), Statement.RETURN_GENERATED_KEYS);
			ps= conn.prepareStatement(SQL.INSERT_CUSTOMER.getQuery(), new String[] {"ID"});
			//first_name, last_name, address, city, state, email
			ps.setString(1,  cust.getFirstName());
			ps.setString(2,  cust.getLastName());
			ps.setString(3,  cust.getAddress());
			ps.setString(4,  cust.getCity());
			ps.setString(5,  cust.getState());
			ps.setString(6,  cust.getEmail());
			ps.setString(7,  cust.getZipcode());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if(rs.next()) {
				
				keyGenerated =  rs.getInt(1);
				
			}
			
		} catch (SQLException e) {
			log.error(String.format("Error at insertCustomer %s" , e.getMessage()));
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at insertCustomer %s" , e.getMessage()));
			}
			
			super.closeConnection();
		}
		
		
		return keyGenerated;
	}

	@Override
	public boolean updateCustomer(Customer cust) {

		PreparedStatement ps= null;
		ResultSet rs =null;
		boolean isUpdated=false;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.UPDATE_CUSTOMER.getQuery());
			//first_name, last_name, address, city, state, email
			ps.setString(1,  cust.getFirstName());
			ps.setString(2,  cust.getLastName());
			ps.setString(3,  cust.getAddress());
			ps.setString(4,  cust.getCity());
			ps.setString(5,  cust.getState());
			ps.setString(6,  cust.getEmail());
			ps.setString(7,  cust.getZipcode());
			ps.setInt(8, cust.getId());
			isUpdated = ps.executeUpdate() >0 ? true : false;
			
		} catch (SQLException e) {
			log.error(String.format("Error at updateCustomer %s" , e.getMessage()));
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at updateCustomer %s" , e.getMessage()));
			}
			
			super.closeConnection();
		}
		
		
		return isUpdated;
	}

	@Override
	public boolean deleteCustomer(Customer cust) {
		
		PreparedStatement ps= null;
		ResultSet rs =null;
		boolean isDeleted=false;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.DELETE_CUSTOMER.getQuery());
			//first_name, last_name, address, city, state, email
			ps.setInt(1, cust.getId());

			isDeleted = ps.executeUpdate() >0 ? true : false;
			
		} catch (SQLException e) {
			log.error(String.format("Error at deleteCustomer %s" , e.getMessage()));
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at deleteCustomer %s" , e.getMessage()));
			}
			
			super.closeConnection();
		}
		
		
		return isDeleted;
	}

}
