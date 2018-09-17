package com.perscholas.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;


public abstract class AbstractDAO {
	static Logger log = Logger.getLogger(AbstractDAO.class);
	
	// Constants
	private static final String SUCCESS_MESSAGE = "connection successful";
	
	protected Connection conn=null ;
	
	
	@Autowired
	DataSource dataSource;
	
	public void getConnection() {
		try {
			log.info("datasource: " + dataSource);
			conn = DataSourceUtils.getConnection(dataSource);
			log.info(SUCCESS_MESSAGE);

		} catch ( Exception e ) {
			log.error(String.format("Failed init a DB Connection: %s", e.getMessage()));
		}
	} 
	
	public void getConnection1() {
		
		try {
			
			
			ClassLoader cl = this.getClass().getClassLoader();
			
			InputStream is = cl.getResourceAsStream("properties/database.properties");
			
			if (is != null) {
				log.info("be read file");
			} else {
				log.error("cannot read file");
			}
			
			Properties prop  = new Properties();
			
			prop.load(is);
			String driver = prop.getProperty("jdbc.driverClassName");
			String url = prop.getProperty("jdbc.url");
			String username = prop.getProperty("jdbc.username");
			String password = prop.getProperty("jdbc.password");
			 
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);

			log.info(SUCCESS_MESSAGE);
		}
		catch ( SQLException e ) {
			log.error("Error when connect database.");
		}
		catch ( ClassNotFoundException e ) {
			log.error("Driver registered is incorrect or driver library not exists in the application");
		}
		catch ( Exception e) {
			log.error(e.getMessage());
		}
	} 

	public void getConnection2() {
		
		try {
			
			
			ClassLoader cl = this.getClass().getClassLoader();
			
			InputStream is = cl.getResourceAsStream("properties/database.properties");
			
			if (is != null) {
				log.info("be read file");
			} else {
				log.error("cannot read file");
			}
			
			Properties prop  = new Properties();
			
			prop.load(is);
			String driver = prop.getProperty("driver");
			String url = prop.getProperty("url");
			String username = prop.getProperty("username");
			String password = prop.getProperty("password");
			 
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);

			log.info(SUCCESS_MESSAGE);
		} catch ( SQLException  e ) {
			log.info("Error when connect database.");
		}
		catch ( ClassNotFoundException exp ) {
			log.info("Driver registered is incorrect or driver library not exists in the application");
		}
		catch ( Exception e) {
			log.info(e.getMessage());
		}
	} 

	public void closeConnection() {

		
		try {
			if(conn!=null) {
				conn.close();
			}
		}catch(SQLException e) {
			log.info("Error close connection in AbstractDAO");
		}
		
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
}
