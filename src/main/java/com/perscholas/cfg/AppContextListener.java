package com.perscholas.cfg;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@WebListener
public class AppContextListener implements ServletContextListener{
	
	static Logger log= Logger.getLogger(AppContextListener.class);
	
	@Autowired
	DataSource ds;
	
	
	@Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("Starting up!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	// ... First close any background tasks which may be using the DB ...
        // ... Then close any DB connection pools ...
    	// Now deregister JDBC drivers in this context's ClassLoader:
        // Get the webapp's ClassLoader
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        // Loop through all drivers
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            if (driver.getClass().getClassLoader() == cl) {
                // This driver was registered by the webapp's ClassLoader, so deregister it:
                try {
                    log.debug("Deregistering JDBC driver: " +  driver);
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException ex) {
                	log.debug("Deregistering JDBC driver ex: " +  ex);
                }
            } else {
                // driver was not registered by the webapp's ClassLoader and may be in use elsewhere
                log.debug("Not deregistering JDBC driver as it does not belong to this webapp's ClassLoader: " + driver);
            }
        }
        
    	ds = null;
    }
}
