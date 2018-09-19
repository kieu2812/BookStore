package com.perscholas.dao;

import java.sql.SQLException;

import com.perscholas.model.Book;

public interface BookDAO {
	enum SQL{

		INSERT_BOOK("INSERT INTO BOOK( NAME, HARDCOVERPATH, ISBN_10, ISBN_13, PUBLISHERID, CATEGORYID, DESCRIPTION, PAGE, QTYINSTOCK) " + 
				"VALUES(?,?,?,?,?,?,?,?,?)"),
		UPDATE_BOOK("UPDATE BOOK SET  NAME=?, HARDCOVERPATH=?,ISBN_10=?, ISBN_13=?, PUBLISHERID=?, " + 
				"CATEGORYID=?, DESCRIPTION=?, PAGE=?, PRICE=? " + 
				" WHERE ID=? and (deleteflag=0 or deleteflag is null) "),
		DELETE_BOOK("{ call deleteBook(?)}"),
		GET_BOOK_BY_NAME("SELECT ID, NAME, HARDCOVERPATH, ISBN_10, ISBN_13, PUBLISHERID, CATEGORYID, DESCRIPTION, PAGE, QTYINSTOCK, PRICE FROM BOOK WHERE NAME=? "),
		GET_BOOK_BY_ID("SELECT ID, NAME, HARDCOVERPATH, ISBN_10, ISBN_13, PUBLISHERID, CATEGORYID, DESCRIPTION, PAGE, QTYINSTOCK,PRICE FROM BOOK WHERE ID=? "),
		GET_BOOK_BY_ISBN_13("SELECT ID, NAME, HARDCOVERPATH, ISBN_10, ISBN_13, PUBLISHERID, CATEGORYID, DESCRIPTION, PAGE, QTYINSTOCK,PRICE FROM BOOK WHERE ISBN_13=? ");
		private final String query;
		private SQL(String s) {
			this.query= s;
			
		}
		public String getQuery() {
			return this.query;
		}
		
	}

	public int insertBook(Book cust) throws SQLException;
	public boolean updateBook(Book cust) throws SQLException;
	public boolean deleteBook(int id);
	public Book getBookByName(String name);
	public Book getBookById(int bookId);
	public Book getBookByIsbn13(String isbn13);
	public Book getBookByIdUseInTransaction(int bookId);
}
