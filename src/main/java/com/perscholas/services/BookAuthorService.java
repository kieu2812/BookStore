package com.perscholas.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.perscholas.dao.AbstractDAO;
import com.perscholas.dao.BookAuthorDAO;
import com.perscholas.model.BookAuthor;

@Service
public class BookAuthorService extends AbstractDAO implements BookAuthorDAO {
	static Logger log = Logger.getLogger(BookAuthorService.class);

	@Override
	public List<BookAuthor> getAllComposings() {
	
		List<BookAuthor> composings=null;
		PreparedStatement ps= null;
		ResultSet rs =null;
		
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_ALL_COMPOSING.getQuery());
			rs = ps.executeQuery();
			composings =new ArrayList<BookAuthor>();
			while(rs.next()) {
				BookAuthor tempBook_Author = new BookAuthor();
				tempBook_Author.setBookid(rs.getInt(1));
				tempBook_Author.setAuthorid(rs.getInt(2));
				composings.add(tempBook_Author);
			}
		} catch (SQLException e) {
			log.error(String.format("Error at getComposingByAuthorId %s ", e.getMessage()));
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				System.err.println("A error occurs when attempt close resources!!!");
				e.printStackTrace();
			}
			
			super.closeConnection();
		}
		
		
		return composings;
	}

	@Override
	public List<BookAuthor> getComposingByAuthorId(int authorId) {
	
		
		List<BookAuthor> composings=null;
		PreparedStatement ps= null;
		ResultSet rs =null;
		
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_COMPOSING_BY_AUTHORID.getQuery());
			ps.setInt(1, authorId);
			rs = ps.executeQuery();
			composings =new ArrayList<BookAuthor>();
			while(rs.next()) {
				BookAuthor tempBook_Author = new BookAuthor();
				tempBook_Author.setBookid(rs.getInt(1));
				tempBook_Author.setAuthorid(rs.getInt(2));
				composings.add(tempBook_Author);
			}
		} catch (SQLException e) {
			log.error(String.format("Error at getComposingByAuthorId %s ", e.getMessage()));
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				System.err.println("A error occurs when attempt close resources!!!");
				e.printStackTrace();
			}
			
			super.closeConnection();
		}
		
		
		return composings;
	}

	@Override
	public List<BookAuthor> getComposingByBookId(int bookId) {
	
		List<BookAuthor> composings=null;
		PreparedStatement ps= null;
		ResultSet rs =null;
		
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_COMPOSING_BY_BOOKID.getQuery());
			ps.setInt(1, bookId);
			rs = ps.executeQuery();
			composings =new ArrayList<BookAuthor>();
			while(rs.next()) {
				BookAuthor tempBook_Author = new BookAuthor();
				tempBook_Author.setBookid(rs.getInt(1));
				tempBook_Author.setAuthorid(rs.getInt(2));
				composings.add(tempBook_Author);
			}
		} catch (SQLException e) {

			log.error(String.format("Error at getComposingByBookId %s ", e.getMessage()));
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at getComposingByBookId %s ", e.getMessage()));
				
			}
			
			super.closeConnection();
		}
		
		
		return composings;
	}

	@Override
	public int insertComposing(BookAuthor book_author) {
	
		PreparedStatement ps= null;
		ResultSet rs =null;
		int keyGenerated=0;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.INSERT_COMPOSING.getQuery(), Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, book_author.getBookid());
			ps.setInt(2, book_author.getAuthorid());
			ps.executeUpdate();
			rs= ps.getGeneratedKeys();
			if(rs.next()) {
				keyGenerated= rs.getInt(1);
			}
			
		} catch (SQLException e) {

			log.error(String.format("Error at getComposingByAuthorId %s ", e.getMessage()));
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at getComposingByAuthorId %s ", e.getMessage()));
			}
			
			super.closeConnection();
		}
		
		
		return keyGenerated;
	}

	@Override
	public boolean updateComposing(BookAuthor newBA, BookAuthor oldBA) {
	
		PreparedStatement ps= null;
		ResultSet rs =null;
		boolean isUpdated=false;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.UPDATE_COMPOSING.getQuery());
			ps.setInt(1, newBA.getBookid());
			ps.setInt(2, newBA.getAuthorid());
			ps.setInt(3, oldBA.getBookid());
			ps.setInt(4, oldBA.getAuthorid());
			isUpdated =ps.executeUpdate()>0 ? true: false;

			
		} catch (SQLException e) {

			log.error(String.format("Error at updateComposing %s ", e.getMessage()));
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at updateComposing %s ", e.getMessage()));
			}
			
			super.closeConnection();
		}
		
		
		return isUpdated;
	}

	@Override
	public boolean removeComposing(BookAuthor book_author) {
	
		PreparedStatement ps= null;
		boolean isDeleted=false;
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.DELETE_COMPOSING.getQuery());
			ps.setInt(1, book_author.getBookid());
			ps.setInt(2, book_author.getAuthorid());
			isDeleted =ps.executeUpdate()>0 ? true: false;

			
		} catch (SQLException e) {

			log.error(String.format("Error at removeComposing %s ", e.getMessage()));
		}finally {
			try {
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at removeComposing %s ", e.getMessage()));
			}
			
			super.closeConnection();
		}
		
		
		return isDeleted;
	}

}
