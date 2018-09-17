package com.perscholas.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perscholas.dao.AbstractDAO;
import com.perscholas.dao.BookDetailInfoDAO;
import com.perscholas.dao.ReviewDAO;
import com.perscholas.model.Book;
import com.perscholas.model.BookDetailInfo;

@Service
public class BookDetailInfoService  extends AbstractDAO implements BookDetailInfoDAO{
	
	static Logger log = Logger.getLogger(BookDetailInfoService.class);
	@Autowired
	ReviewDAO reviewService;
	
	@Override
	public BookDetailInfo getBookById(int bookId) {
		PreparedStatement ps= null;
		ResultSet rs =null;
		BookDetailInfo bookDetail =null;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_BOOK_BY_ID.getQuery());
			
			//SELECT B.ID, B.NAME, HARDCOVERPATH, PRICE, A.NAME AS AUTHOR, ISBN_10, ISBN_13, P.NAME AS PUBLISHER, DESCRIPTION, PAGE, QTYINSTOCK, " + 
			//"         NVL(R.AVGRATING,0), TOTALREVIEW        " + 
			ps.setInt(1, bookId );
			rs = ps.executeQuery();
			
			while(rs.next()) {
				bookDetail = new BookDetailInfo();
				Book book = new Book();
				
				book.setId(rs.getInt(1));
				book.setName(rs.getString(2));
				book.setHardCoverPath(rs.getString(3));
				book.setPrice(rs.getDouble(4));
				bookDetail.setAuthor(rs.getString(5));
				book.setISBN_10(rs.getString(6));
				book.setISBN_13(rs.getString(7));
				bookDetail.setPublisher(rs.getString(8));
				book.setDescription(rs.getString(9));
				book.setPages(rs.getInt(10));
				book.setQtyInStock(rs.getInt(11));
				book.setRating(rs.getInt(12));
				book.setTotalReview(rs.getInt(13));
				bookDetail.setBook(book);
				bookDetail.setListReviews(reviewService.getAllReviewsByBookId(bookId));
				
								
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
		
		
		return bookDetail;
	}
}
