package com.perscholas.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.perscholas.dao.AbstractDAO;
import com.perscholas.dao.BookInfoDAO;
import com.perscholas.model.BookInfo;
import com.perscholas.model.PaginationResult;

@Service
public class BookInfoService  extends AbstractDAO implements BookInfoDAO{
	static Logger log = Logger.getLogger(BookInfoService.class);

	@Override
	public BookInfo getByBookId(int bookid) {
		PreparedStatement ps= null;
		ResultSet rs =null;
		BookInfo bookInfo =null;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_BOOK_BY_ID.getQuery());
			
			//SELECT B.ID, B.NAME, HARDCOVERPATH, PRICE
			ps.setInt(1, bookid );
			rs = ps.executeQuery();
			
			if(rs.next()) {	
				bookInfo= new BookInfo();
				bookInfo.setId(rs.getInt(1));
				bookInfo.setName(rs.getString(2));
				bookInfo.setHardCoverPath(rs.getString(3));
				bookInfo.setPrice(rs.getDouble(4));
				bookInfo.setQtyInStock(rs.getInt(5));
				bookInfo.setRating(rs.getFloat(6));
				bookInfo.setTotalReview(rs.getInt(7));
			}
		} catch (SQLException e) {
			log.error(String.format("Error at getBookById %s ", e.getMessage()));

		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at getBookById %s ", e.getMessage()));

			}
			
			super.closeConnection();
		}
		
		
		return bookInfo;
	}
	@Override
	public List<BookInfo> getAllBooks() {

		List<BookInfo> books= new ArrayList<>();
		PreparedStatement ps= null;
		ResultSet rs =null;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_ALL_BOOKS.getQuery());
			/*
			"SELECT B.ID, B.NAME, HARDCOVERPATH, PRICE,NVL(QTYINSTOCK,0)," + 
				"         NVL(R.AVGRATING,0), TOTALREVIEW       " + 
			" From BOOK B " + 
				" LEFT JOIN (SELECT BOOKID, ROUND(AVG(RATING),1)  AS AVGRATING , COUNT(BOOKID) AS TOTALREVIEW " + 
				"        FROM REVIEW R " + 
				"        GROUP BY BOOKID " + 
				"    ) R ON R.BOOKID = B.ID "
			*/ 	
			
			rs= ps.executeQuery();
			while(rs.next()) {
				BookInfo bookInfo = new BookInfo();
				bookInfo.setId(rs.getInt(1));
				bookInfo.setName(rs.getString(2));
				bookInfo.setHardCoverPath(rs.getString(3));
				bookInfo.setPrice(rs.getDouble(4));
				bookInfo.setQtyInStock(rs.getInt(5));
				bookInfo.setRating(rs.getFloat(6));
				bookInfo.setTotalReview(rs.getInt(7));
				books.add(bookInfo);
			}

			
		} catch (SQLException e) {

			log.error(String.format("Error at getAllBooks %s ", e.getMessage()));

		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at getAllBooks %s ", e.getMessage()));

			}
			
			super.closeConnection();
		}
		
		
		return books;
	}
	
	public PaginationResult<BookInfo> getAllBooksPagination(int page, int maxResult, int maxNavigationPage) {
		List<BookInfo> books= getAllBooks();
	
		return new PaginationResult<BookInfo>(books, page, maxResult, maxNavigationPage);
	}


	@Override
	public List<BookInfo> findBook(String keyword) {

		List<BookInfo> books =new ArrayList<>();
		PreparedStatement ps= null;
		ResultSet rs =null;
		BookInfo bookInfo=null;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.FIND_BOOK.getQuery());
		/*	SELECT B.ID, B.NAME, HARDCOVERPATH, PRICE, QTYINSTOCK, " + 
				"         NVL(R.AVGRATING,0),NVL(TOTALREVIEW,0)     " + 
				"From BOOK B  " + 
				"INNER JOIN CATEGORY C ON C.ID= B.CATEGORYID " + 
				"LEFT JOIN (SELECT BOOKID, ROUND(AVG(RATING),1) AS AVGRATING, COUNT(BOOKID) AS TOTALREVIEW " + 
				"        FROM REVIEW R  " + 
				"        GROUP BY BOOKID  " + 
				"    ) R ON R.BOOKID = B.ID " + 			
				"WHERE ( trim(UPPER(B.NAME)) LIKE ? OR trim(UPPER(C.NAME))  LIKE ?  OR B.ISBN_10=? OR B.ISBN_13=?) 
				AND (B.DELETEFLAG is null or B.DELETEFLAG!=1 ) ";
			*/
			
			ps.setString(1, "%"+ keyword.toUpperCase().trim() +"%");
			ps.setString(2, "%"+ keyword.toUpperCase().trim() +"%");
			ps.setString(3, keyword.trim());
			ps.setString(4, keyword.trim());
			
			rs= ps.executeQuery();
			while(rs.next()) {
				bookInfo = new BookInfo();
				bookInfo.setId(rs.getInt(1));
				bookInfo.setName(rs.getString(2));
				bookInfo.setHardCoverPath(rs.getString(3));
				bookInfo.setPrice(rs.getDouble(4));
				bookInfo.setQtyInStock(rs.getInt(5));
				bookInfo.setRating(rs.getFloat(6));
				bookInfo.setTotalReview(rs.getInt(7));
				books.add(bookInfo);
			}
		} catch (SQLException e) {

			log.error(String.format("Error at getAllBooks %s ", e.getMessage()));
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at getAllBooks %s ", e.getMessage()));

			}
			
			super.closeConnection();
		}
		return books;
	
	}
	@Override
	public List<BookInfo> getBestSellerBooks() {

		List<BookInfo> books= new ArrayList<>();
		PreparedStatement ps= null;
		ResultSet rs =null;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_BEST_SELLER.getQuery());
			/*
			"WITH OD AS("   +    
				" SELECT * FROM ( " +
				"      SELECT BOOKID, SUM(QUANTITY) TOTAL " +   
				"      FROM ORDER_DETAIL " +
				"      GROUP BY BOOKID " +
				"       ORDER BY SUM(QUANTITY)  DESC " +
				"     ) " +
				"  WHERE ROWNUM<=10 " +   
				" )      " +
				" SELECT B.ID, B.NAME, HARDCOVERPATH, PRICE,NVL(QTYINSTOCK,0), " +
				" 			        NVL(R.AVGRATING,0), TOTALREVIEW, OD.TOTAL   " +
				" 				From BOOK B INNER JOIN OD ON OD.BOOKID= B.ID       " +
				" 				 LEFT JOIN (SELECT BOOKID, ROUND(AVG(RATING),1)  AS AVGRATING , COUNT(BOOKID) AS TOTALREVIEW " + 
				" 				        FROM REVIEW R   " +
				" 				        GROUP BY BOOKID  " +
				" 			    ) R ON R.BOOKID = B.ID " +
				          
				" 			WHERE DELETEFLAG is null or B.DELETEFLAG!=1 " 
			*/ 	
			
			rs= ps.executeQuery();
			while(rs.next()) {
				BookInfo bookInfo = new BookInfo();
				bookInfo.setId(rs.getInt(1));
				bookInfo.setName(rs.getString(2));
				bookInfo.setHardCoverPath(rs.getString(3));
				bookInfo.setPrice(rs.getDouble(4));
				bookInfo.setQtyInStock(rs.getInt(5));
				bookInfo.setRating(rs.getFloat(6));
				bookInfo.setTotalReview(rs.getInt(7));
				books.add(bookInfo);
			}

			
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at getBestSellerBooks %s ", e.getMessage()));

			}
			
			super.closeConnection();
		}
		
		
		return books;
	}
	@Override
	public List<BookInfo> getByCategoryId(int categoryId) {
		PreparedStatement ps= null;
		ResultSet rs =null;
		List<BookInfo> list =new ArrayList<>();
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_BOOK_BY_CATEGORY_ID.getQuery());
			
			//SELECT B.ID, B.NAME, HARDCOVERPATH, PRICE
			ps.setInt(1, categoryId );
			rs = ps.executeQuery();
			
			while(rs.next()) {	
				BookInfo bookInfo =  new BookInfo();
				bookInfo= new BookInfo();
				bookInfo.setId(rs.getInt(1));
				bookInfo.setName(rs.getString(2));
				bookInfo.setHardCoverPath(rs.getString(3));
				bookInfo.setPrice(rs.getDouble(4));
				bookInfo.setQtyInStock(rs.getInt(5));
				bookInfo.setRating(rs.getFloat(6));
				bookInfo.setTotalReview(rs.getInt(7));
				list.add(bookInfo);
			}
		} catch (SQLException e) {
			log.error(String.format("Error at getBestSellerBooks %s ", e.getMessage()));

		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at getBestSellerBooks close resource %s ", e.getMessage()));

			}
			
			super.closeConnection();
		}
		
		
		return list;
	}
}
