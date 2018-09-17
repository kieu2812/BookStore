package com.perscholas.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.perscholas.dao.AbstractDAO;
import com.perscholas.dao.AuthorDAO;
import com.perscholas.model.Author;

@Service
public class AuthorService extends AbstractDAO implements AuthorDAO{
	static Logger log = Logger.getLogger(AuthorService.class);

	public List<Author> getAllAuthors() {
		List<Author> authors=null;
	
		
		super.getConnection();
		try (PreparedStatement ps= conn.prepareStatement(SQL.GET_ALL_AUTHOR.getQuery())){
			
			try(ResultSet rs = ps.executeQuery()){
				authors =new ArrayList<>();
				while(rs.next()) {
					Author tempAuthor = new Author();
					tempAuthor.setId(rs.getInt("id"));
					tempAuthor.setName(rs.getString("name"));
					authors.add(tempAuthor);
				}
			}
		} catch (SQLException e) {
			log.error(String.format("Error at getAllAuthors %s" , e.getMessage()));
		}finally {
					
			super.closeConnection();
		}
		
		
		return authors;
	}

	@Override
	public Author getAuthorById(int id) {
		Author author=null;
		
		super.getConnection();
		try(PreparedStatement ps= conn.prepareStatement(SQL.GET_AUTHOR_BY_ID.getQuery())) {
		
			
			ps.setInt(1, id);
			try(ResultSet rs = ps.executeQuery()){
			
				if(rs.next()) {
					author = new Author();
					author.setId(rs.getInt("id"));
					author.setName(rs.getString("name"));
					
				}
			}
		} catch (SQLException e) {

			log.error(String.format("Error at getAuthorById %s", e.getMessage()));
		}finally {
			super.closeConnection();
		}
		
		return author;
	}

	@Override
	public Author getAuthorByName(String name) {
		Author author = null;
		try(PreparedStatement ps= conn.prepareStatement(SQL.GET_EXACT_AUTHOR_BY_NAME.getQuery())) {
			super.getConnection();
		
			ps.setString(1, name);
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					author = new Author();
					author = new Author();
					author.setId(rs.getInt("id"));
					author.setName(rs.getString("name"));
							
				}
			}
		} catch (SQLException e) {
			log.info(String.format("Error at getAuthorByName %s" , e.getMessage()));

		}finally {
			super.closeConnection();
		}
		
		return author;
	}
	@Override
	public List<Author> findByName(String name) {
		List<Author> authors=new ArrayList<>();
	
		
		try(PreparedStatement ps= conn.prepareStatement(SQL.FIND_AUTHOR_BY_NAME.getQuery())) {
			String lowerName= name.toLowerCase();
			super.getConnection();
			
			ps.setString(1, "%"+lowerName +"%");
			try(ResultSet rs = ps.executeQuery()){
				
				while(rs.next()) {
					Author author = new Author();
					author = new Author();
					author.setId(rs.getInt("id"));
					author.setName(rs.getString("name"));
					authors.add(author);		
					log.info("Author= "+ author);
				}
			}
		} catch (SQLException e) {
			
			log.error(String.format("Error at findByName %s" , e.getMessage()));
			
		}finally {
			super.closeConnection();
		}
		
		return authors;
	}

	/**
	 * Insert name of author to author table 
	 * @param name - Name of author will insert to author table
	 * @return value is a new id of author inserted or not. If it is 0, mean cannot insert. 
	 */
	@Override
	public int addAuthor(String name) {
		int autoId= 0;
	
		
		try (PreparedStatement ps= conn.prepareStatement(SQL.INSERT_AUTHOR.getQuery(),  new String [] {"ID"})){
			AuthorService authorDAO=  new AuthorService();
			Author authors= authorDAO.getAuthorByName(name);
			
			// If name not exists in author table then insert
			
			if(authors!=null) {
				super.getConnection();

				
				
				ps.setString(1, name);
				ps.executeUpdate();
				try (ResultSet rs = ps.getGeneratedKeys()){
					
					if(rs.next()) {
						autoId =  rs.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		}finally {			
			super.closeConnection();
		}
		return autoId;
	}
/***
 * 
 */
	
	@Override
	public boolean updateAuthor(int id, String newName) {
		boolean recordAffect= false;
		PreparedStatement ps=null;
		try {
			// If name not exists in author table then insert
			super.getConnection();
			ps= conn.prepareStatement(SQL.UPDATE_AUTHOR_BY_ID.getQuery());
			ps.setString(1, newName);
			ps.setInt(2, id);
			recordAffect = (ps.executeUpdate() >0 );						
			
		} catch (SQLException e) {
			
			log.error("Error at updateAuthor " + e.getMessage());
		}finally {
			try {
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at updateAuthor %s" , e.getMessage()));
			
			}
			super.closeConnection();
		}
		return recordAffect;
	}

	@Override
	public boolean removeAuthor(int id) {
		boolean recordAffect= false;
		PreparedStatement ps=null;
		try {
		
				super.getConnection();
			    ps= conn.prepareStatement(SQL.DELETE_AUTHOR_BY_ID.getQuery());
				ps.setInt(1, id);
				recordAffect = (ps.executeUpdate() > 0 );						

		} catch (SQLException e) {
			
			log.error("Error at removeAuthor " + e.getMessage());
		}finally {
			try {
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at removeAuthor %s" , e.getMessage()));
			}
			super.closeConnection();
		}
		return recordAffect;
	}

}
