package com.perscholas.dao;

import java.util.List;

import com.perscholas.model.Author;

public interface  AuthorDAO {
	enum SQL{
		GET_ALL_AUTHOR("Select * from author"),
		GET_AUTHOR_BY_ID("SELECT * FROM AUTHOR WHERE ID=?"),
		GET_EXACT_AUTHOR_BY_NAME("SELECT * FROM AUTHOR WHERE NAME= ?"),
		FIND_AUTHOR_BY_NAME("SELECT * FROM AUTHOR WHERE lower(NAME) LIKE ?"),
		INSERT_AUTHOR("INSERT INTO AUTHOR(NAME) VALUES(?)"),
		UPDATE_AUTHOR_BY_ID("UPDATE AUTHOR SET NAME=? WHERE ID=? "),
		DELETE_AUTHOR_BY_ID("DELETE FROM AUTHOR WHERE ID=?");
		
		private final String query;
		private SQL(String s){
			this.query= s;
			
		}
		public String getQuery() {
			return this.query;
		}
		
	}
	public List<Author> getAllAuthors();
	public Author getAuthorById(int id);
	public Author getAuthorByName(String name);
	public int addAuthor(String name);
	public boolean updateAuthor(int id, String newName);
	public boolean removeAuthor(int id);
	public List<Author> findByName(String name);
}
