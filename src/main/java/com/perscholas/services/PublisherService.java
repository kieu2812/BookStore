package com.perscholas.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.perscholas.dao.AbstractDAO;
import com.perscholas.dao.PublisherDAO;
import com.perscholas.model.Publisher;

@Service

public class PublisherService extends AbstractDAO implements PublisherDAO{
	static Logger log = Logger.getLogger(PublisherService.class);

	@Override
	public List<Publisher> getAllPublishers() {
		
		List<Publisher> publishers=null;
		PreparedStatement ps= null;
		ResultSet rs =null;
		
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_ALL_PUBLISHERS.getQuery());
			rs = ps.executeQuery();
			publishers =new ArrayList<Publisher>();
			while(rs.next()) {
				Publisher tempPublisher = new Publisher();
				//ID, NAME, ADDRESS, CITY, STATE, PHONE
				tempPublisher.setId(rs.getInt(1));
				tempPublisher.setName(rs.getString(2));
				tempPublisher.setAddress(rs.getString(3));
				tempPublisher.setCity(rs.getString(4));
				tempPublisher.setState(rs.getString(5));
				tempPublisher.setPhone(rs.getString(6));
				publishers.add(tempPublisher);
			}
		} catch (SQLException e) {
			log.error(String.format("Error at getAllPublishers %s" , e.getMessage()));

		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at getAllPublishers %s" , e.getMessage()));
			}
			
			super.closeConnection();
		}
		
		
		return publishers;
	}

	@Override
	public Publisher getPublisherById(int id) {
		
		PreparedStatement ps= null;
		ResultSet rs =null;
		Publisher tempPublisher = new Publisher();
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_PUBLISHER_BY_ID.getQuery());
			ps.setInt(1, id);
			rs = ps.executeQuery();
		
			if(rs.next()) {
			
				//ID, NAME, ADDRESS, CITY, STATE, PHONE
				tempPublisher.setId(rs.getInt(1));
				tempPublisher.setName(rs.getString(2));
				tempPublisher.setAddress(rs.getString(3));
				tempPublisher.setCity(rs.getString(4));
				tempPublisher.setState(rs.getString(5));
				tempPublisher.setPhone(rs.getString(6));
			}
		} catch (SQLException e) {
			log.error(String.format("Error at getPublisherById %s" , e.getMessage()));
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at getPublisherById %s" , e.getMessage()));

			}
			
			super.closeConnection();
		}
		
		
		return tempPublisher;
	}

	@Override
	public Publisher getPublishersByName(String name) {
		
		PreparedStatement ps= null;
		ResultSet rs =null;
		Publisher tempPublisher = new Publisher();
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_PUBLISHERS_BY_NAME.getQuery());
			ps.setString(1, name);
			rs = ps.executeQuery();
		
			if(rs.next()) {
			
				//ID, NAME, ADDRESS, CITY, STATE, PHONE
				tempPublisher.setId(rs.getInt(1));
				tempPublisher.setName(rs.getString(2));
				tempPublisher.setAddress(rs.getString(3));
				tempPublisher.setCity(rs.getString(4));
				tempPublisher.setState(rs.getString(5));
				tempPublisher.setPhone(rs.getString(6));
			}
		} catch (SQLException e) {
			log.error(String.format("Error at getPublishersByName %s" , e.getMessage()));
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at getPublishersByName %s" , e.getMessage()));

			}
			
			super.closeConnection();
		}
		
		
		return tempPublisher;	
	}

	@Override
	public List<Publisher> findPublishersByName(String name) {
		List<Publisher> publishers=null;
		PreparedStatement ps= null;
		ResultSet rs =null;
		Publisher tempPublisher = new Publisher();
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.FIND_PUBLISHERS_BY_NAME.getQuery());
			ps.setString(1, name);
			rs = ps.executeQuery();
			publishers = new ArrayList<>();
			while(rs.next()) {
			
				//ID, NAME, ADDRESS, CITY, STATE, PHONE
				tempPublisher.setId(rs.getInt(1));
				tempPublisher.setName(rs.getString(2));
				tempPublisher.setAddress(rs.getString(3));
				tempPublisher.setCity(rs.getString(4));
				tempPublisher.setState(rs.getString(5));
				tempPublisher.setPhone(rs.getString(6));
				publishers.add(tempPublisher);
			}
		} catch (SQLException e) {
			log.error(String.format("Error at findPublishersByName %s" , e.getMessage()));
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at findPublishersByName %s" , e.getMessage()));

			}
			
			super.closeConnection();
		}
		
		
		return publishers;
	}

	@Override
	public List<Publisher> findPublishersByCity(String city) {
		List<Publisher> publishers= new ArrayList<>();
		PreparedStatement ps= null;
		ResultSet rs =null;
		Publisher tempPublisher = new Publisher();
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.FIND_PUBLISHERS_BY_CITY.getQuery());
			ps.setString(1, city);
			rs = ps.executeQuery();
			while(rs.next()) {
			
				//ID, NAME, ADDRESS, CITY, STATE, PHONE
				tempPublisher.setId(rs.getInt(1));
				tempPublisher.setName(rs.getString(2));
				tempPublisher.setAddress(rs.getString(3));
				tempPublisher.setCity(rs.getString(4));
				tempPublisher.setState(rs.getString(5));
				tempPublisher.setPhone(rs.getString(6));
				publishers.add(tempPublisher);
			}
		} catch (SQLException e) {
			log.error(String.format("Error at findPublishersByName %s" , e.getMessage()));
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at findPublishersByName %s" , e.getMessage()));

			}
			
			super.closeConnection();
		}
		
		
		return publishers;
	}

	@Override
	public List<Publisher> findPublishersByAddress(String address) {
		
		List<Publisher> publishers= new ArrayList<>();
		PreparedStatement ps= null;
		ResultSet rs =null;
		Publisher tempPublisher = new Publisher();
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.FIND_PUBLISHERS_BY_ADDRESS.getQuery());
			ps.setString(1, address);
			rs = ps.executeQuery();
			while(rs.next()) {
			
				//ID, NAME, ADDRESS, CITY, STATE, PHONE
				tempPublisher.setId(rs.getInt(1));
				tempPublisher.setName(rs.getString(2));
				tempPublisher.setAddress(rs.getString(3));
				tempPublisher.setCity(rs.getString(4));
				tempPublisher.setState(rs.getString(5));
				tempPublisher.setPhone(rs.getString(6));
				publishers.add(tempPublisher);
			}
		} catch (SQLException e) {
			log.error(String.format("Error at findPublishersByName %s" , e.getMessage()));
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at findPublishersByName %s" , e.getMessage()));
			}
			
			super.closeConnection();
		}
		
		
		return publishers;
	}

	@Override
	public List<Publisher> findPublishersByState(String state) {
		List<Publisher> publishers= new ArrayList<>();
		PreparedStatement ps= null;
		ResultSet rs =null;
		Publisher tempPublisher = new Publisher();
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.FIND_PUBLISHERS_BY_ADDRESS.getQuery());
			ps.setString(1, state);
			rs = ps.executeQuery();
			while(rs.next()) {
			
				//ID, NAME, ADDRESS, CITY, STATE, PHONE
				tempPublisher.setId(rs.getInt(1));
				tempPublisher.setName(rs.getString(2));
				tempPublisher.setAddress(rs.getString(3));
				tempPublisher.setCity(rs.getString(4));
				tempPublisher.setState(rs.getString(5));
				tempPublisher.setPhone(rs.getString(6));
				publishers.add(tempPublisher);
			}
		} catch (SQLException e) {
			log.error(String.format("Error at findPublishersByName %s" , e.getMessage()));
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at findPublishersByName %s" , e.getMessage()));

			}
			
			super.closeConnection();
		}
		
		
		return publishers;
	}

	@Override
	public List<Publisher> findPublishersByPhone(String phone) {
		
		List<Publisher> publishers= new ArrayList<>();
		PreparedStatement ps= null;
		ResultSet rs =null;
		Publisher tempPublisher = new Publisher();
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.FIND_PUBLISHERS_BY_ADDRESS.getQuery());
			ps.setString(1, phone);
			rs = ps.executeQuery();
			while(rs.next()) {
			
				//ID, NAME, ADDRESS, CITY, STATE, PHONE
				tempPublisher.setId(rs.getInt(1));
				tempPublisher.setName(rs.getString(2));
				tempPublisher.setAddress(rs.getString(3));
				tempPublisher.setCity(rs.getString(4));
				tempPublisher.setState(rs.getString(5));
				tempPublisher.setPhone(rs.getString(6));
				publishers.add(tempPublisher);
			}
		} catch (SQLException e) {
			log.error(String.format("Error at findPublishersByName %s" , e.getMessage()));

		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at findPublishersByName %s" , e.getMessage()));

			}
			
			super.closeConnection();
		}
		
		
		return publishers;
	}

	@Override
	public int insertPublisher(Publisher pub) {
		PreparedStatement ps= null;
		ResultSet rs =null;
		int keyGenerated=0;
		try {
			super.getConnection();
			//ps= conn.prepareStatement(SQL.INSERT_PUBLISHER.getQuery(), Statement.RETURN_GENERATED_KEYS);
			ps= conn.prepareStatement(SQL.INSERT_PUBLISHER.getQuery(),new String[] {"ID"});

			// NAME, ADDRESS, CITY, STATE, PHONE 
			ps.setString(1, pub.getName());
			ps.setString(2, pub.getAddress());
			ps.setString(3, pub.getCity());
			ps.setString(4, pub.getState());
			ps.setString(5, pub.getPhone());
			ps.executeUpdate();
			rs= ps.getGeneratedKeys();
			
			if(rs.next()) {
				keyGenerated= rs.getInt(1);
			}
		} catch (SQLException e) {
			log.error(String.format("Error at insertPublisher %s" , e.getMessage()));

		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at insertPublisher %s" , e.getMessage()));

			}
			
			super.closeConnection();
		}
		
		
		return keyGenerated;	
	}

	@Override
	public boolean updatePublisher(Publisher pub) {
		
		PreparedStatement ps= null;
		ResultSet rs =null;
		boolean isUpdated=false;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.UPDATE_PUBLISHER.getQuery());
		//UPDATE PUBLISHER SET  NAME=?, ADDRESS=?, CITY=?, STATE=?, PHONE=? WHERE ID=?"
			ps.setString(1, pub.getName());
			ps.setString(2, pub.getAddress());
			ps.setString(3, pub.getCity());
			ps.setString(4, pub.getState());
			ps.setString(5, pub.getPhone());
			ps.setInt(6, pub.getId());
			isUpdated= ps.executeUpdate() >0 ? true :false;
		} catch (SQLException e) {
			log.error(String.format("Error at updatePublisher %s" , e.getMessage()));

		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at updatePublisher %s" , e.getMessage()));

			}
			
			super.closeConnection();
		}
		
		
		return isUpdated;	
	}

	@Override
	public boolean deletePublisher(int id) {
		
		PreparedStatement ps= null;
		ResultSet rs =null;
		boolean isDeleted=false;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.DELETE_PUBLISHER.getQuery());
			ps.setInt(1, id);
			isDeleted= ps.executeUpdate() >0 ? true :false;
		} catch (SQLException e) {
			log.error(String.format("Error at deletePublisher %s" , e.getMessage()));

		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at deletePublisher %s" , e.getMessage()));

			}
			
			super.closeConnection();
		}
		
		
		return isDeleted;	
	}

	@Override
	public Map<Integer, String> getAllNames() {
		Map<Integer, String> publishers=null;
		PreparedStatement ps= null;
		ResultSet rs =null;
		
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_ALL_PUBLISHERS.getQuery());
			rs = ps.executeQuery();
			publishers =new HashMap<>();
			while(rs.next()) {
				publishers.put(rs.getInt(1), rs.getString(2));
			}
		} catch (SQLException e) {
			log.error(String.format("Error at getAllNames %s" , e.getMessage()));

		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at getAllNames %s" , e.getMessage()));

			}
			
			super.closeConnection();
		}
		
		
		return publishers;
	}

}
