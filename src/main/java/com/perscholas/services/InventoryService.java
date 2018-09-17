package com.perscholas.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.perscholas.dao.AbstractDAO;
import com.perscholas.dao.InventoryDAO;
import com.perscholas.model.Inventory;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service

public class InventoryService extends AbstractDAO implements InventoryDAO {

	static Logger log = Logger.getLogger(InventoryService.class);

	public List<Inventory> getAllItemsInStock() {
		List<Inventory> items=new ArrayList<Inventory>();
		PreparedStatement ps= null;
		ResultSet rs =null;
		
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_ALL_ITEMS_INSTOCK.getQuery());
			rs = ps.executeQuery();
			while(rs.next()) {
				Inventory item = new Inventory();
				item.setBarcode(rs.getString(1));
				item.setBookid(rs.getInt(2));
				item.setIsAvailable(true);
				items.add(item);
			}
		} catch (SQLException e) {
			
			log.error(String.format("Error at getAllItemsInStock %s" , e.getMessage()));

		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at getAllItemsInStock %s" , e.getMessage()));

			}
			
			super.closeConnection();
		}
		return items;
	
	}

	
	public List<Inventory> getItemsByBookID(int bookId) {
		List<Inventory> items=new ArrayList<Inventory>();
		PreparedStatement ps= null;
		ResultSet rs =null;
		
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_ITEMS_BY_BOOKID.getQuery());
			ps.setInt(1, bookId);
			rs = ps.executeQuery();
			while(rs.next()) {
				Inventory item = new Inventory();
				item.setBarcode(rs.getString(1));
				item.setBookid(rs.getInt(2));
				item.setIsAvailable(true);
				items.add(item);
			}
		} catch (SQLException e) {
			
			log.error(String.format("Error at getItemsByBookID %s" , e.getMessage()));

		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at getItemsByBookID %s" , e.getMessage()));

			}
			
			super.closeConnection();
		}
		return items;
	}

	
	public boolean updateaItemAvailable(boolean isAvailable, String barcode) {
	
		PreparedStatement ps= null;
		boolean affectedRows=false;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.UPDATE_ITEM_ISAVAILABLE.getQuery());
			ps.setInt(1, isAvailable ? 1 : 0);
			ps.setString(2, barcode);	
			affectedRows= ps.executeUpdate() >0 ? true : false;
			
		} catch (SQLException e) {
			
			log.error(String.format("Error at updateaItemAvailable %s" , e.getMessage()));

		}finally {
			try {
				if(ps!=null)	ps.close();
			}catch(SQLException e) {
				log.error(String.format("Error at updateaItemAvailable %s" , e.getMessage()));

			}
			
			super.closeConnection();
		}
		return affectedRows;
	}
	
	public boolean insertItem(String barcode, int bookId) {
		PreparedStatement ps= null;
		boolean affectedRows=false;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.INSERT_ITEM.getQuery());
			
			ps.setInt(1, bookId);
			ps.setString(2, barcode);
			affectedRows= ps.executeUpdate() >0 ? true : false;
			
		} catch (SQLException e) {
			
			log.error(String.format("Error at insertItem %s" , e.getMessage()));

		}finally {
			try {
				if(ps!=null)	ps.close();
			}catch(SQLException e) {
				log.error(String.format("Error at insertItem %s" , e.getMessage()));

			}
			
			super.closeConnection();
		}
		return affectedRows;
		
	}

	
	public boolean deleteItem(String barcode) {
		PreparedStatement ps= null;
		boolean affectedRows=false;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.DELETE_ITEM.getQuery());
			ps.setString(1, barcode);
			affectedRows= ps.executeUpdate() >0 ? true : false;
			
		} catch (SQLException e) {
			log.error(String.format("Error at deleteItem %s" , e.getMessage()));

		}finally {
			try {
				if(ps!=null)	ps.close();
			}catch(SQLException e) {
				log.error(String.format("Error at deleteItem %s" , e.getMessage()));

			}
			
			super.closeConnection();
		}
		return affectedRows;
		}



	public boolean isValidItem(String barcode) {
		PreparedStatement ps= null;
		ResultSet rs=null;
		boolean isValid=false;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.CHECK_VALID_ITEM_BY_BARCODE.getQuery());
			ps.setString(1, barcode);
			rs= ps.executeQuery() ;
			if(rs.next() && rs.getInt(1)==1) 	isValid=true;
			
			
		} catch (SQLException e) {
			
			log.error(String.format("Error at isValidItem %s" , e.getMessage()));

		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
			}catch(SQLException e) {
				log.error(String.format("Error at isValidItem %s" , e.getMessage()));

			}
			
			super.closeConnection();
		}
		return isValid;
		
	}

	
}
