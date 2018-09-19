package com.perscholas.services;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.perscholas.dao.AbstractDAO;
import com.perscholas.dao.BookDAO;
import com.perscholas.model.Book;

@Service
public class BookService extends AbstractDAO implements BookDAO{

	static Logger log =  Logger.getLogger(BookService.class);

	@Override
	public int insertBook(Book book) throws SQLException{
		PreparedStatement ps= null;
		ResultSet rs= null;
		int keyGenerated=0;
		try {
			super.getConnection();

			ps= conn.prepareStatement(SQL.INSERT_BOOK.getQuery(), new String[] {"ID"});
			/*
			INSERT_BOOK("INSERT INTO BOOK( NAME, HARDCOVERPATH, ISBN_10, ISBN_13, PUBLISHERID, CATEGORYID, DESCRIPTION, PAGE) " + 
				"VALUES(?,?,?,?,?,?,?,?,?)"),
		
			*/ 
			ps.setString(1, book.getName());
			ps.setString(2, book.getHardCoverPath());
			ps.setString(3, book.getISBN_10());
			ps.setString(4, book.getISBN_13());
			ps.setInt(5, book.getPublisherId());
			ps.setInt(6, book.getCategoryId());
			ps.setString(7, book.getDescription());
			ps.setInt(8, book.getPages());
			ps.executeUpdate();
			rs= ps.getGeneratedKeys();
			if(rs.next()) {
				keyGenerated= rs.getInt(1);
			}
			
		

		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at insertBook %s" , e.getMessage()));		
			}
			
		//super.closeConnection();
		}
		return keyGenerated;
	}

	@Override
	public boolean updateBook(Book book) throws SQLException{
		
		
		PreparedStatement ps= null;
		boolean isUpdated=false;
		try {
			super.getConnection();
	
			ps= conn.prepareStatement(SQL.UPDATE_BOOK.getQuery());
			/*
			UPDATE BOOK SET  NAME=?, HARDCOVERPATH=?,ISBN_10=?, ISBN_13=?, PUBLISHERID=?, " + 
				"CATEGORYID=?, DESCRIPTION=?, PAGE=?, PRICE=? " + 
				"WHERE ID=? AND DELETEFLAG=0 
			*/ 
			log.info("NEW BOOK INFOMATION TO BE UPDATED " +book);
			ps.setString(1, book.getName());
			ps.setString(2, book.getHardCoverPath());
			ps.setString(3, book.getISBN_10());
			ps.setString(4, book.getISBN_13());
			ps.setInt(5, book.getPublisherId());
			ps.setInt(6, book.getCategoryId());
			ps.setString(7, book.getDescription());
			ps.setInt(8, book.getPages());
			ps.setDouble(9,book.getPrice());
			ps.setInt(10, book.getId());

			log.info("Preparestatement Query"+ps.toString());
			int rowCountExecute=ps.executeUpdate() ;
			log.info("Row count executed in SQL UPDATE BOOK TABLE="+ rowCountExecute);
			isUpdated =rowCountExecute >0 ? true : false;
		

		}finally {
			try {
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at updateBook %s ", e.getMessage()));
			}
			
		//	super.closeConnection();
		}
		
		
		return isUpdated;
	}

	@Override
	public boolean deleteBook(int bookId) {
		CallableStatement callableStat= null;
		
		boolean isDeleted=false;
		try {
			super.getConnection();
			callableStat= conn.prepareCall(SQL.DELETE_BOOK.getQuery());
			callableStat.setInt(1, bookId);
		
			isDeleted =callableStat.executeUpdate()>0 ? true: false;

		} catch (SQLException e) {

			log.error(" Error at deleteBook "+ e.getMessage());
		}finally {
			try {
				if(callableStat!=null)	callableStat.close();
				
			}catch(SQLException e) {
				log.error("A error occurs when attempt close resources!!!");
				log.error(e.getMessage());
			}
			
			super.closeConnection();
		}
		
		
		return isDeleted;
	}
	

	public Book getBookByName(String name) {
		PreparedStatement ps= null;
		ResultSet rs =null;
		Book book= null;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_BOOK_BY_NAME.getQuery());
			/*
			 * GET_BOOK_BY_NAME("SELECT ID, NAME, HARDCOVERPATH, ISBN_10, ISBN_13, PUBLISHERID, CATEGORYID, DESCRIPTION, PAGE, QTYINSTOCK, PRICE FROM BOOK WHERE NAME=? ");
			*/ 
			ps.setString(1, name);
			rs =ps.executeQuery();
			if(rs.next()) {
				book= new Book();
				book.setId(rs.getInt(1));
				book.setName(rs.getString(2));
				book.setHardCoverPath(rs.getString(3));
				book.setISBN_10(rs.getString(4));
				book.setISBN_13(rs.getString(5));
				book.setPublisherId(rs.getInt(6));
				book.setCategoryId(rs.getInt(7));
				book.setDescription(rs.getString(8));
				book.setPages(rs.getInt(9));
				book.setQtyInStock(rs.getInt(10));
				book.setPrice(rs.getDouble(11));
			}

			
		} catch (SQLException e) {
			log.error("A error occurs when attempt getBookByName");
			log.error(e.getMessage());
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error("A error occurs when attempt close resources!!!");
				log.error(e.getMessage());
			}
			
			super.closeConnection();
		}
		
		
		return book;

	}

	@Override
	public Book getBookByIdUseInTransaction(int bookId) {
		PreparedStatement ps= null;
		ResultSet rs =null;
		Book book= null;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_BOOK_BY_ID.getQuery());
			/*
			 * GET_BOOK_BY_NAME("SELECT ID, NAME, HARDCOVERPATH, ISBN_10, ISBN_13, PUBLISHERID, CATEGORYID, DESCRIPTION, PAGE, QTYINSTOCK FROM BOOK WHERE NAME=? ");
			*/ 
			ps.setInt(1, bookId);
			rs =ps.executeQuery();
			if(rs.next()) {
				book= new Book();
				book.setId(rs.getInt(1));
				book.setName(rs.getString(2));
				book.setHardCoverPath(rs.getString(3));
				book.setISBN_10(rs.getString(4));
				book.setISBN_13(rs.getString(5));
				book.setPublisherId(rs.getInt(6));
				book.setCategoryId(rs.getInt(7));
				book.setDescription(rs.getString(8));
				book.setPages(rs.getInt(9));
				book.setQtyInStock(rs.getInt(10));
				book.setPrice(rs.getDouble(11));

			}

			
		} catch (SQLException e) {
			log.error("A error occurs when attempt getBookById");
			log.error(e.getMessage());
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error("A error occurs when attempt close resources!!!");
				log.error(e.getMessage());
			}
			
			//super.closeConnection();
		}
		
		
		return book;

	}

	public Book getBookById(int bookId) {
		PreparedStatement ps= null;
		ResultSet rs =null;
		Book book= null;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_BOOK_BY_ID.getQuery());
			/*
			 * GET_BOOK_BY_NAME("SELECT ID, NAME, HARDCOVERPATH, ISBN_10, ISBN_13, PUBLISHERID, CATEGORYID, DESCRIPTION, PAGE, QTYINSTOCK FROM BOOK WHERE NAME=? ");
			*/ 
			ps.setInt(1, bookId);
			rs =ps.executeQuery();
			if(rs.next()) {
				book= new Book();
				book.setId(rs.getInt(1));
				book.setName(rs.getString(2));
				book.setHardCoverPath(rs.getString(3));
				book.setISBN_10(rs.getString(4));
				book.setISBN_13(rs.getString(5));
				book.setPublisherId(rs.getInt(6));
				book.setCategoryId(rs.getInt(7));
				book.setDescription(rs.getString(8));
				book.setPages(rs.getInt(9));
				book.setQtyInStock(rs.getInt(10));
				book.setPrice(rs.getDouble(11));

			}

			
		} catch (SQLException e) {
			log.error("A error occurs when attempt getBookById");
			log.error(e.getMessage());
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error("A error occurs when attempt close resources!!!");
				log.error(e.getMessage());
			}
			
			//super.closeConnection();
		}
		
		
		return book;

	}

	@Override
	public Book getBookByIsbn13(String isbn13) {
		PreparedStatement ps= null;
		ResultSet rs =null;
		Book book= null;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_BOOK_BY_ID.getQuery());
			/*
			 * GET_BOOK_BY_NAME("SELECT ID, NAME, HARDCOVERPATH, ISBN_10, ISBN_13, PUBLISHERID, CATEGORYID, DESCRIPTION, PAGE, QTYINSTOCK FROM BOOK WHERE NAME=? ");
			*/ 
			ps.setString(1, isbn13);
			rs =ps.executeQuery();
			if(rs.next()) {
				book= new Book();
				book.setId(rs.getInt(1));
				book.setName(rs.getString(2));
				book.setHardCoverPath(rs.getString(3));
				book.setISBN_10(rs.getString(4));
				book.setISBN_13(rs.getString(5));
				book.setPublisherId(rs.getInt(6));
				book.setCategoryId(rs.getInt(7));
				book.setDescription(rs.getString(8));
				book.setPages(rs.getInt(9));
				book.setQtyInStock(rs.getInt(10));
				book.setPrice(rs.getDouble(11));

			}

			
		} catch (SQLException e) {
			log.error("A error occurs when attempt getBookByISBN13");
			log.error(e.getMessage());
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error("A error occurs when attempt close resources!!!");
				log.error(e.getMessage());
			}
			
			super.closeConnection();
		}
		
		
		return book;
	}


}
