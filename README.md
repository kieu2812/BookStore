This is a commercial book store web application that allows customer to order books on-line. The application will support features for both customer (users) and administration. 

Basically, the application is implemented using: 
	Java 8
	Spring Frameworks 5 as Spring MVC, Spring Rest, Spring Security, Spring Transaction, Spring Validation 
	Hibernate
	JDBC and Connection pool  
	Oracle 12 
	Tomcat server 8
	Maven
	Eclipse and SQL Developer

The home page will display top best books. Customer access the website be able to search books by name; see what kinds of category book; review policy, order books, etc. 

Customer has to register an account before making an order. Registered password will be encoded using Spring Security Crypto BCrypt. The application will be using Spring Security to authenticate if an user existed. 
There is limit login time feature that user account will be locked if continuously failed login, for example 3 times. This is only feature that implemented by Hibernate.

Making an order will perform multiple operations such as create order, order detail, shipping detail and credit card info so they all are performed in one transaction using Spring Transaction.

As an administration role, you are be able to add new book, update book and delete book. You also able review customer orders and reports.

The input forms are validated using Spring Validation and DAOs/Services are tested by JUnit.


-----------------------------------------------------------------------------------------------

In order to import this project onto your development, you need: 
	Download the application codes from this Git-hub.
	Use the latest Eclipse as preferred to create Maven Spring web project, JDK 1.8
	Import/copy all java codes and xml files onto your new project. Update pom file.
	Update properties file (application, database, log, etc.) for connection info, image path info, log info.
	Install Oracle 12 and run the enclosed export database script to create schema and test data. Also see the attached database model for information.
	Make-up book images with the same image name storing in Database Book table and copy to the configured image path
	Build project using Maven.
	Create Tomcat server inside Eclipse, deploy and run your project on server. 
	There are two DataSource beans (jdbc connection & connection pool) in Spring-Database.xml -> choose one. Remember to copy Oracle Driver Manager to Tomcat server lib folder if choosing connection pool.
	
	
In order to run this application on you own servers, you need:
	Update connection info in context.xml under META-INF that will be used by server to create connection pool.
	Update application.properties file to include your image path.
	Generate a WAR file using Maven and deploy onto Tomcat server webapps folder. 
	Since the application is getting database connection from server connection pool so you need to deploy Driver Manager library under server lib folder.
	Start server and access project URL from your browser.
	 	
		
