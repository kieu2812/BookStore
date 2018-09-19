package com.perscholas.dao;

import java.util.List;

import com.perscholas.model.Review;

public interface ReviewDAO {
	enum SQL{
		GET_BOOK_BY_ID("select r.id,c.first_name || ' ' || c.last_name as posterName, r.comments, r.createdate, r.rating "+ 
	
				" from review r inner join customer c on r.posterid = c.id where bookid=? order by r.createdate desc"),
		INSERT_REVIEW("insert into review( bookid, posterid, comments, createdate, rating ) values(?,?,?,?,?)"),
		UPDATE_REVIEW("update review set comments=?, rating= ?, createdate=? where id=?"),
		DELETE_REVIEW("Delete from review where id=?");
		private final String query;
		private SQL(String s) {
			this.query= s;
			
		}
		public String getQuery() {
			return this.query;
		}
		
	}
	public List<Review> getAllReviewsByBookId(int bookId);
	public boolean insertReview(Review review);
	public boolean updateReview(Review review);
	public boolean deleteReview(int id);
	
}
