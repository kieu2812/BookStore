package com.perscholas.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.perscholas.dao.AbstractDAO;
import com.perscholas.dao.CategoryDAO;
import com.perscholas.model.Category;

@Service
public class CategoryService extends AbstractDAO implements CategoryDAO {
	
	@Autowired 
	CategoryDAO categoryDAO;
	
	static Logger log = Logger.getLogger(CategoryService.class);

	@Bean
	public List<Category> getAllCategories() {
		List<Category> categories = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			super.getConnection();
			ps = conn.prepareStatement(SQL.GET_ALL_CATEGORY.getQuery());
			rs = ps.executeQuery();
			categories = new ArrayList<>();
			while (rs.next()) {
				Category tempCategory = new Category();
				tempCategory.setId(rs.getInt("id"));
				tempCategory.setName(rs.getString("name"));
				categories.add(tempCategory);
			}
		} catch (SQLException e) {

			log.error("Error at getAllCategories " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();

			} catch (SQLException e) {
				log.error("A error occurs when attempt close resources!!!");
				log.error(e.getMessage());
			}

			super.closeConnection();
		}

		return categories;
	}

	@Override
	public Category getCategoryById(int id) {
		Category Category = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			super.getConnection();
			ps = conn.prepareStatement(SQL.GET_CATEGORY_BY_ID.getQuery());
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				Category = new Category();
				Category.setId(rs.getInt("id"));
				Category.setName(rs.getString("name"));

			}
		} catch (SQLException e) {

			log.error("Error at getCategoryById " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();

			} catch (SQLException e) {
				log.error("A error occurs when attempt close resources!!!");
				log.error(e.getMessage());
			}

			super.closeConnection();
		}

		return Category;
	}

	@Override
	public Category getCategoryByName(String name) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		Category category = null;
		try {
			super.getConnection();
			ps = conn.prepareStatement(SQL.GET_EXACT_CATEGORY_BY_NAME.getQuery());
			category = new Category();
			ps.setString(1, name);
			rs = ps.executeQuery();

			if (rs.next()) {
				category.setId(rs.getInt("id"));
				category.setName(rs.getString("name"));
			}
		} catch (SQLException e) {

			log.error("Error at getCategoryByName " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();

			} catch (SQLException e) {
				log.error("A error occurs when attempt close resources!!!");
				log.error(e.getMessage());
			}

			super.closeConnection();
		}

		return category;
	}

	@Override
	public List<Category> findByName(String name) {
		List<Category> categorys = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String lowerName = name.toLowerCase();
			super.getConnection();
			ps = conn.prepareStatement(SQL.FIND_CATEGORY_BY_NAME.getQuery());
			ps.setString(1, "%" + lowerName + "%");
			rs = ps.executeQuery();

			while (rs.next()) {
				Category category = new Category();
				category = new Category();
				category.setId(rs.getInt("id"));
				category.setName(rs.getString("name"));
				categorys.add(category);
				log.info("Category= " + category);
			}
		} catch (SQLException e) {

			log.error("Error at findByName " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();

			} catch (SQLException e) {
				log.error("A error occurs at findByName when attempt close resources!!!");
				log.error(e.getMessage());
			}

			super.closeConnection();
		}

		return categorys;
	}

	/**
	 * Insert name of Category to Category table
	 * 
	 * @param name
	 *            - Name of Category will insert to Category table
	 * @return value is a new id of Category inserted or not. If it is 0, mean
	 *         cannot insert.
	 */
	@Override
	public int addCategory(String name) {
		int autoId = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			// If name not exists in Category table then insert

			if (categoryDAO.getCategoryByName(name) == null) {
				super.getConnection();

				ps = conn.prepareStatement(SQL.INSERT_CATEGORY.getQuery(), new String[] { "ID" });
				ps.setString(1, name);

				ps.executeUpdate();
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					autoId = rs.getInt(1);
				}

			}
		} catch (SQLException e) {

			log.error("Error at addCategory" + e.getMessage());
		} finally {
			try {

				if (ps != null)
					ps.close();

			} catch (SQLException e) {
				log.error("A error occurs  at addCategory when attempt close resources!!!");
				log.error(e.getMessage());
			}

			super.closeConnection();
		}
		return autoId;
	}

	/***
	 * 
	 */

	@Override
	public boolean updateCategory(int id, String newName) {
		boolean recordAffect = false;
		PreparedStatement ps = null;

		try {

			// If name not exists in Category table then insert
			super.getConnection();
			ps = conn.prepareStatement(SQL.UPDATE_CATEGORY_BY_ID.getQuery());
			ps.setInt(1, id);
			ps.setString(2, newName);
			recordAffect = ps.executeUpdate() > 0 ? true : false;

		} catch (SQLException e) {

			log.error(e.getMessage());
		} finally {
			try {

				if (ps != null)
					ps.close();

			} catch (SQLException e) {
				log.error("A error occurs at updateCategory when attempt close resources!!!");
				log.error(e.getMessage());
			}

			super.closeConnection();
		}
		return recordAffect;
	}

	@Override
	public boolean removeCategory(int id) {
		boolean recordAffect = false;
		PreparedStatement ps = null;
		try {
			super.getConnection();
			ps = conn.prepareStatement(SQL.UPDATE_CATEGORY_BY_ID.getQuery());
			ps.setInt(1, id);
			recordAffect = ps.executeUpdate() > 0 ? true : false;

		} catch (SQLException e) {
			log.error("Error at removeCategory " + e.getMessage());
		} finally {
			try {
				if (ps != null)
					ps.close();

			} catch (SQLException e) {
				log.error("A error occurs at removeCategory when attempt close resources!!!");
				log.error(e.getMessage());
			}

			super.closeConnection();
		}
		return recordAffect;
	}

}
