package com.perscholas.services;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.perscholas.dao.AbstractDAO;
import com.perscholas.dao.PriceHistoryDAO;
import com.perscholas.model.PriceHistory;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service

public class PriceHistoryService extends AbstractDAO implements PriceHistoryDAO {
	static Logger log = Logger.getLogger(PriceHistoryService.class);

	@Override
	public List<PriceHistory> getPriceHistoryById(int bookId) {
		List<PriceHistory> list = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			super.getConnection();
			ps = conn.prepareStatement(SQL.GET_HISTORY_PRICE.getQuery());
			ps.setInt(1, bookId);
			rs = ps.executeQuery();

			// SELECT BOOKID, STARTDATE, ENDDATE, INVOICEPRICE, SELLPRICE,CREATEDATE FROM
			// PRICE_HISTORY WHERE BOOKID=?
			while (rs.next()) {
				PriceHistory temp = new PriceHistory();
				temp.setBookid(rs.getInt(1));
				temp.setStartDate(rs.getDate(2).toLocalDate());
				temp.setEndDate(rs.getDate(3) != null ? rs.getDate(3).toLocalDate() : null);
				temp.setInvoicePrice(rs.getDouble(4));
				temp.setSellPrice(rs.getDouble(5));
				temp.setCreateDate(rs.getDate(6).toLocalDate());
				list.add(temp);
			}

		} catch (SQLException e) {

			log.info(e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();

			} catch (SQLException e) {
				log.info("A error occurs when attempt close resources!!!");
				log.info(e.getMessage());
			}

		}

		return list;
	}

	@Override
	public PriceHistory getCurrentPriceById(int bookId) {
		PriceHistory temp = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			super.getConnection();
			ps = conn.prepareStatement(SQL.GET_CURRENT_PRICE_BY_BOOKID.getQuery());
			ps.setInt(1, bookId);
			rs = ps.executeQuery();

			// SELECT BOOKID, STARTDATE, ENDDATE, INVOICEPRICE, SELLPRICE,CREATEDATE FROM
			// PRICE_HISTORY WHERE BOOKID=?
			while (rs.next()) {
				temp = new PriceHistory();
				temp.setBookid(rs.getInt(1));
				temp.setStartDate(rs.getDate(2).toLocalDate());
				temp.setEndDate(rs.getDate(3) != null ? rs.getDate(3).toLocalDate() : null);
				temp.setInvoicePrice(rs.getDouble(4));
				temp.setSellPrice(rs.getDouble(5));
				temp.setCreateDate(rs.getDate(6).toLocalDate());
			}

		} catch (SQLException e) {

			log.info(e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();

			} catch (SQLException e) {
				log.info("A error occurs when attempt close resources!!!");
				log.info(e.getMessage());
			}

		}

		return temp;
	}

	@Override
	public boolean insertUpdatePrice(int bookId, double invoicePrice, double sellPrice) throws SQLException {
		/* after insert new price to price_history 
		 * Default enddate is null that represent for record apply market price at
		 * current time
		*/
		boolean isSuccess = false;
/*		PreparedStatement psGet =null;
		PreparedStatement psUpdate = null;
		
		PreparedStatement psInsert = null;*/
		
		// init a connection
		
		CallableStatement callableSt=null;
		try {
			super.getConnection();
			/*   v_bookId int;
				 v_invoice NUMBER(6,2);
				 v_sell NUMBER(6,2); 
			 * */
			callableSt = conn.prepareCall(SQL.SAVE_PRICE_HISTORY.getQuery());
			callableSt.setInt(1, bookId);
			callableSt.setDouble(2, invoicePrice);
			callableSt.setDouble(3, sellPrice);
			isSuccess= (callableSt.executeUpdate() >0);
			
		
		}finally {
			
				if (callableSt!=null ) callableSt.close();
		
		}
		
/*		
		try 
			
		 {
			psGet = conn.prepareStatement(SQL.GET_CURRENT_PRICE_BY_BOOKID.getQuery());
			log.info("bookid = " + bookId);
			log.info("invoice price = " + invoicePrice);
			log.info("sell Price = " + sellPrice);
			log.info(new Date(System.currentTimeMillis()));
			
			// check this book is insert new price today or not
			psGet.setInt(1, bookId);
			
			int result;
	
			try (ResultSet rsGet = psGet.executeQuery()) {
				if (rsGet.next() && rsGet.getDate(2).toLocalDate().compareTo(LocalDate.now()) == 0) {
					psUpdate = conn.prepareStatement(SQL.UPDATE_NEW_PRICE.getQuery());
					psUpdate.setDouble(1, invoicePrice);
					psUpdate.setDouble(2, sellPrice);
					psUpdate.setInt(3, bookId);
					result = psUpdate.executeUpdate();
				} else {
					// else this book didn't insert new price today, go to insert new price
					psInsert= conn.prepareStatement(SQL.INSERT_PRICE.getQuery());
					psInsert.setInt(1, bookId);
					psInsert.setDate(2, new Date(System.currentTimeMillis()));
					psInsert.setDouble(3, invoicePrice);
					psInsert.setDouble(4, sellPrice);
					psInsert.setDate(5, new Date(System.currentTimeMillis()));
					result = psInsert.executeUpdate();
				}
				
				isInserted = (result > 0);
			}finally {

				try {
					if (psInsert!=null ) psInsert.close();
					if (psUpdate!=null ) psUpdate.close();
				} catch (SQLException e) {
					log.error(String.format("Error at insertUpdatePrice when try to insert or update Price History %s" , e.getMessage() ));
				}
			}
		}
		catch (SQLException exp) {
			
			log.error(exp.getMessage());
			
		}finally {
			
				try {
					if (psGet!=null ) psGet.close();
				} catch (SQLException e) {
					log.error(String.format("Error at insertUpdatePrice %s" , e.getMessage() ));
				}
			
		}
	*/
		
		return isSuccess;
	}

}
