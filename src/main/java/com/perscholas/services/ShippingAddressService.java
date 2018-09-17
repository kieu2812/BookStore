package com.perscholas.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.perscholas.dao.AbstractDAO;
import com.perscholas.dao.ShippingAddressDAO;
import com.perscholas.model.ShippingAddress;

@Service

public class ShippingAddressService extends AbstractDAO implements ShippingAddressDAO{

	static Logger log = Logger.getLogger(ShippingAddressService.class);

	@Override
	public List<ShippingAddress> getAllByCustId(int custId) {
	
		List<ShippingAddress> list=new ArrayList<ShippingAddress>();
		PreparedStatement ps= null;
		ResultSet rs =null;
		
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_SHIPPING_ADDRESS_BY_CUST_ID.getQuery());
			ps.setInt(1, custId);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				ShippingAddress temp = new ShippingAddress();
				//id, first_name, last_name, address, city, state, email
				temp.setId(rs.getInt("id"));
				temp.setAddress(rs.getString("address"));
				temp.setCity(rs.getString("city"));
				temp.setState(rs.getString("state"));
				temp.setZipcode(rs.getString("zipcode"));
				list.add(temp);
			}
		} catch (SQLException e) {
			log.error(String.format("Error at getAllByCustId %s" , e.getMessage() ));

		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at getAllByCustId %s" , e.getMessage() ));

			}
			
			super.closeConnection();
		}
		
		
		return list;
	}

	@Override
	public ShippingAddress getShipping_AddressById(int id) {

		PreparedStatement ps= null;
		ResultSet rs =null;
		ShippingAddress shipping =  null;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_SHIPPING_ADDRESS_BY_ID.getQuery());
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				shipping = new ShippingAddress();
				//id, first_name, last_name, address, city, state, email
				shipping.setId(rs.getInt("id"));
				shipping.setAddress(rs.getString("address"));
				shipping.setCity(rs.getString("city"));
				shipping.setState(rs.getString("state"));
				shipping.setZipcode(rs.getString("zipcode"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				System.err.println("A error occurs when attempt close resources!!!");
				e.printStackTrace();
			}
			
			super.closeConnection();
		}
		return shipping;
		
	}

	@Override
	public int insertShipping_Address(ShippingAddress shipping) throws SQLException {
	
		PreparedStatement ps= null;
		ResultSet rs =null;
		int keyGenerated = 0;
		try {
			super.getConnection();
			//ps= conn.prepareStatement(SQL.INSERT_SHIPPING_ADDRESS.getQuery(), Statement.RETURN_GENERATED_KEYS);
			ps= conn.prepareStatement(SQL.INSERT_SHIPPING_ADDRESS.getQuery(), new String [] {"ID"});
			ps.setString(1, shipping.getAddress());
			ps.setString(2, shipping.getCity());
			ps.setString(3, shipping.getState());
			ps.setString(4, shipping.getZipcode());
			ps.execute();
			rs = ps.getGeneratedKeys();
			
			
			if(rs.next()) {
				keyGenerated=rs.getInt(1);
				//System.out.println(rs.getLong(1));
				//System.out.println("keyGenerated" + keyGenerated);
			}
		} finally {
			if(rs!=null)	rs.close();
			if(ps!=null)	ps.close();
			
			//super.closeConnection();
		}
		return keyGenerated;
		
	}

	@Override
	public boolean updateShipping_Address(ShippingAddress shipping) {


		PreparedStatement ps= null;
		ResultSet rs =null;
		boolean isUpdate=false;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_SHIPPING_ADDRESS_BY_ID.getQuery());
			ps.setString(1, shipping.getAddress());
			ps.setString(2, shipping.getCity());
			ps.setString(3, shipping.getState());
			ps.setString(4, shipping.getZipcode());
			ps.setInt(5, shipping.getId());
			isUpdate = ps.executeUpdate()>0 ? true : false;
			
			
			
		
		} catch (SQLException e) {
			log.error(String.format("Error at insertReview %s" , e.getMessage() ));

		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at insertReview %s" , e.getMessage() ));

			}
			
			super.closeConnection();
		}
		return isUpdate;
	}

	@Override
	public boolean deleteShipping_Address(ShippingAddress shipping) {
	

		PreparedStatement ps= null;
		ResultSet rs =null;
		boolean isDelete=false;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_SHIPPING_ADDRESS_BY_ID.getQuery());
		
			ps.setInt(1, shipping.getId());
			isDelete = ps.executeUpdate()>0 ? true : false;
			
			
			
		
		} catch (SQLException e) {
			log.error(String.format("Error at insertReview %s" , e.getMessage() ));

		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at insertReview %s" , e.getMessage() ));

			}
			
			super.closeConnection();
		}
		return isDelete;
	}

	@Override
	public int insertInTransaction(ShippingAddress shipping_Address) throws SQLException {
		
		return 0;
	}


}
