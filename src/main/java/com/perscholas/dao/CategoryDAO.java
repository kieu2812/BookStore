package com.perscholas.dao;

import java.util.List;

import com.perscholas.model.Category;

public interface CategoryDAO {
	enum SQL{
		GET_ALL_CATEGORY("Select * from category"),
		GET_CATEGORY_BY_ID("SELECT * FROM category WHERE ID=?"),
		GET_EXACT_CATEGORY_BY_NAME("SELECT * FROM category WHERE NAME = ?"),
		FIND_CATEGORY_BY_NAME("SELECT * FROM CATEGORY WHERE lower(NAME) LIKE ?"),
		INSERT_CATEGORY("INSERT INTO category(NAME) VALUES(?)"),
		UPDATE_CATEGORY_BY_ID("UPDATE category SET NAME=? WHERE ID=? "),
		DELETE_CATEGORY_BY_ID("DELETE FROM category WHERE ID=?");
		private final String query;
		private SQL(String s){
			this.query= s;
			
		}
		public String getQuery() {
			return this.query;
		}
		
	}
	public List<Category> getAllCategories();
	public Category getCategoryById(int id);
	public Category getCategoryByName(String name);
	public int addCategory(String name);
	public boolean updateCategory(int id, String newName);
	public boolean removeCategory(int id);
	public List<Category> findByName(String name);

}
