package com.perscholas.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.perscholas.dao.AbstractDAO;
import com.perscholas.dao.ReviewDAO;
import com.perscholas.model.Review;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ReviewService extends AbstractDAO implements ReviewDAO {
	static Logger log = Logger.getLogger(ReviewService.class);

	@Override
	public List<Review> getAllReviewsByBookId(int bookId) {
		
		List<Review> reviews=null;
		PreparedStatement ps= null;
		ResultSet rs =null;
		
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_BOOK_BY_ID.getQuery());
			ps.setInt(1, bookId );
			rs = ps.executeQuery();
			reviews =new ArrayList<Review>();
			while(rs.next()) {
				Review tempReview = new Review();
				// id, bookid, posterid, comments, createdate, rating
				tempReview.setId(rs.getInt("id"));
				tempReview.setPosterName(rs.getString("posterName"));
				tempReview.setCreateDate(rs.getDate("createdate"));
				tempReview.setComments(rs.getString("comments"));
				tempReview.setRating(rs.getInt("rating"));
				reviews.add(tempReview);
			}
		} catch (SQLException e) {
			log.error(String.format("Error at getAllReviewsByBookId %s" , e.getMessage() ));
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at getAllReviewsByBookId %s" , e.getMessage() ));

			}
			
			super.closeConnection();
		}
		
		
		return reviews;

	}

	@Override
	public boolean insertReview(Review review) {
		
		PreparedStatement ps= null;
		boolean isInserted= false;
		
		try {
			super.getConnection();
			//bookid, posterid, comments, createdate, rating 
			ps= conn.prepareStatement(SQL.INSERT_REVIEW.getQuery());
			ps.setInt(1, review.getBookid());
			ps.setInt(2, review.getPosterid());
			ps.setString(3, review.getComments());
			ps.setDate(4, review.getCreateDate());
			ps.setInt(5, review.getRating());
			
			
			isInserted = ps.executeUpdate() >0 ? true : false;

		} catch (SQLException e) {
			log.error(String.format("Error at insertReview %s" , e.getMessage() ));

		}finally {
			try {
				
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at insertReview %s" , e.getMessage() ));

			}
			
			super.closeConnection();
		}
		
		
		return isInserted;
	}

	@Override
	public boolean updateReview(Review review) {
		PreparedStatement ps= null;
		boolean isUpdated= false;
		
		try {
			super.getConnection();
			//comments=?, rating= ?, createdate=? 
			ps= conn.prepareStatement(SQL.UPDATE_REVIEW.getQuery());
			ps.setString(1, review.getComments());			
			ps.setInt(2, review.getRating());
			ps.setDate(3, review.getCreateDate());
			ps.setInt(4, review.getId());
			isUpdated = ps.executeUpdate() >0 ? true : false;

		} catch (SQLException e) {
			log.error(String.format("Error at updateReview %s" , e.getMessage() ));

		}finally {
			try {
				
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at updateReview %s" , e.getMessage() ));

			}
			
			super.closeConnection();
		}
		
		
		return isUpdated;
	}

	@Override
	public boolean deleteReview(int id) {
	
		PreparedStatement ps= null;
		boolean isDeleted= false;
		
		try {
			super.getConnection();
			//comments=?, rating= ?, createdate=? 
			ps= conn.prepareStatement(SQL.DELETE_REVIEW.getQuery());

			ps.setInt(1, id);
			isDeleted = ps.executeUpdate() >0 ? true : false;

		} catch (SQLException e) {
			log.error(String.format("Error at deleteReview %s" , e.getMessage() ));

		}finally {
			try {
				
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at deleteReview %s" , e.getMessage() ));

			}
			
			super.closeConnection();
		}
		
		
		return isDeleted;
	}

}
