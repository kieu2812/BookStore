package com.perscholas.dao;

import java.util.List;

import com.perscholas.model.BookAuthor;

public interface BookAuthorDAO {
	enum SQL{
		GET_ALL_COMPOSING("SELECT BOOKID, AUTHORID FROM BOOK_AUTHOR"),
		GET_COMPOSING_BY_AUTHORID("SELECT BOOKID, AUTHORID FROM BOOK_AUTHOR WHERE AUTHORID=?"),
		GET_COMPOSING_BY_BOOKID("SELECT BOOKID, AUTHORID FROM BOOK_AUTHOR WHERE BOOKID= ?"),
		INSERT_COMPOSING("INSERT INTO BOOK_AUTHOR(BOOKID, AUTHORID) VALUES(?,?)"),
		UPDATE_COMPOSING("UPDATE BOOK_AUTHOR SET AUTHORID=?, BOOKID=? WHERE BOOKID=? AND AUTHORID=?"),
		DELETE_COMPOSING("DELETE FROM BOOK_AUTHOR WHERE BOOKID=? AND AUTHORID=?");
		
		private final String query;
		private SQL(String s){
			this.query= s;
			
		}
		public String getQuery() {
			return this.query;
		}
		
	}
	public List<BookAuthor> getAllComposings();
	public List<BookAuthor> getComposingByAuthorId(int authorId);
	public List<BookAuthor> getComposingByBookId(int bookID);
	public int insertComposing(BookAuthor book_author);
	public boolean updateComposing(BookAuthor newBA, BookAuthor oldBA);
	public boolean removeComposing(BookAuthor book_author);
	

}
