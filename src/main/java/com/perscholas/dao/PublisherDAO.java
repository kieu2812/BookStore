package com.perscholas.dao;

import java.util.List;
import java.util.Map;

import com.perscholas.model.Publisher;

public interface PublisherDAO {
	enum SQL{
		GET_ALL_PUBLISHERS("SELECT ID, NAME, ADDRESS, CITY, STATE, PHONE FROM PUBLISHER"),
		GET_ALL_NAME("SELECT ID, NAME FROM PUBLISHER"),
		GET_PUBLISHER_BY_ID("SELECT ID, NAME, ADDRESS, CITY, STATE, PHONE FROM PUBLISHER WHERE ID=?"),
		FIND_PUBLISHERS_BY_NAME("SELECT ID, NAME, ADDRESS, CITY, STATE, PHONE FROM PUBLISHER WHERE NAME LIKE ?"),
		GET_PUBLISHERS_BY_NAME("SELECT ID, NAME, ADDRESS, CITY, STATE, PHONE FROM PUBLISHER WHERE NAME = ?"),
		FIND_PUBLISHERS_BY_ADDRESS("SELECT ID, NAME, ADDRESS, CITY, STATE, PHONE FROM PUBLISHER WHERE ADDRESS LIKE ?"),
		FIND_PUBLISHERS_BY_CITY("SELECT ID, NAME, ADDRESS, CITY, STATE, PHONE FROM PUBLISHER WHERE CITY LIKE  ?"),
		FIND_PUBLISHERS_BY_STATE("SELECT ID, NAME, ADDRESS, CITY, STATE, PHONE FROM PUBLISHER WHERE STATE LIKE ?"),
		FIND_PUBLISHERS_BY_PHONE("SELECT ID, NAME, ADDRESS, CITY, STATE, PHONE FROM PUBLISHER WHERE PHONE LIKE ?"),
		INSERT_PUBLISHER("INSERT INTO PUBLISHER( NAME, ADDRESS, CITY, STATE, PHONE ) VALUES(?,?,?,?,?)"),
		UPDATE_PUBLISHER("UPDATE PUBLISHER SET  NAME=?, ADDRESS=?, CITY=?, STATE=?, PHONE=? WHERE ID=?"),
		DELETE_PUBLISHER("DELETE FROM PUBLISHER WHERE ID=?");
		
		private final String query;
		private SQL(String s){
			this.query= s;
			
		}
		public String getQuery() {
			return this.query;
		}
		
	}
	public List<Publisher> getAllPublishers();
	public Map<Integer, String> getAllNames();
	public Publisher getPublisherById(int id);
	
	public Publisher getPublishersByName(String name);
	public List<Publisher> findPublishersByName(String name);
	public List<Publisher> findPublishersByCity(String city);
	public List<Publisher> findPublishersByAddress(String address);
	public List<Publisher> findPublishersByState(String state);
	public List<Publisher> findPublishersByPhone(String phone);
	public int insertPublisher(Publisher pub);
	public boolean updatePublisher(Publisher pub);
	public boolean deletePublisher(int id);
}
