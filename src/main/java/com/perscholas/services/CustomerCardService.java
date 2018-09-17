package com.perscholas.services;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.perscholas.dao.AbstractDAO;
import com.perscholas.dao.CustomerCardDAO;
import com.perscholas.model.CustomerCard;

@Service
public class CustomerCardService extends AbstractDAO implements CustomerCardDAO {

	@Override
	public List<CustomerCard> getAllCards() {
		List<CustomerCard> cards = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			super.getConnection();
			ps = conn.prepareStatement(SQL.GET_ALL_CARDS.getQuery());
			rs = ps.executeQuery();
			cards = new ArrayList<CustomerCard>();
			while (rs.next()) {
				CustomerCard tempCard = new CustomerCard();
				// CARDID, CARDNUMBER, HOLDERNAME, EXPIREDATE, SECURITYCODE, CUSTOMERID
				tempCard.setCardId(rs.getInt("CARDID"));
				tempCard.setCardNumber(rs.getString("CARDNUMBER"));
				tempCard.setHolderName(rs.getString("HOLDERNAME"));
				tempCard.setExpireDate(rs.getDate("EXPIREDATE"));
				tempCard.setSecurityCode(rs.getInt("SECURITYCODE"));
				tempCard.setCustomerId(rs.getInt("CUSTOMERID"));
				tempCard.setExpireMonth(rs.getInt("expireMonth"));
				tempCard.setExpireYear(rs.getInt("expireYear"));
				cards.add(tempCard);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();

			} catch (SQLException e) {
				System.err.println("A error occurs when attempt close resources!!!");
				e.printStackTrace();
			}

			super.closeConnection();
		}

		return cards;
	}

	@Override
	public List<CustomerCard> getCardsByCustId(int custid) {
		List<CustomerCard> cards = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			super.getConnection();
			ps = conn.prepareStatement(SQL.GET_CARDS_BY_CUSTID.getQuery());
			ps.setInt(1, custid);

			rs = ps.executeQuery();
			cards = new ArrayList<CustomerCard>();
			while (rs.next()) {
				CustomerCard tempCard = new CustomerCard();
				// CARDID, CARDNUMBER, HOLDERNAME, EXPIREDATE, SECURITYCODE, CUSTOMERID
				tempCard.setCardId(rs.getInt("CARDID"));
				tempCard.setCardNumber(rs.getString("CARDNUMBER"));
				tempCard.setHolderName(rs.getString("HOLDERNAME"));
				tempCard.setExpireDate(rs.getDate("EXPIREDATE"));
				tempCard.setSecurityCode(rs.getInt("SECURITYCODE"));
				tempCard.setCustomerId(rs.getInt("CUSTOMERID"));
				tempCard.setExpireMonth(rs.getInt("expireMonth"));
				tempCard.setExpireYear(rs.getInt("expireYear"));

				cards.add(tempCard);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();

			} catch (SQLException e) {
				System.err.println("A error occurs when attempt close resources!!!");
				e.printStackTrace();
			}

			super.closeConnection();
		}

		return cards;
	}

	@Override
	public CustomerCard getCartByCardId(int cardId) {
		CustomerCard card = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			super.getConnection();
			ps = conn.prepareStatement(SQL.GET_CARD_BY_CARTID.getQuery());
			ps.setInt(1, cardId);

			rs = ps.executeQuery();
			if (rs.next()) {
				card = new CustomerCard();
				// CARDID, CARDNUMBER, HOLDERNAME, EXPIREDATE, SECURITYCODE, CUSTOMERID
				card.setCardId(rs.getInt("CARDID"));
				card.setCardNumber(rs.getString("CARDNUMBER"));
				card.setHolderName(rs.getString("HOLDERNAME"));
				card.setExpireDate(rs.getDate("EXPIREDATE"));
				card.setSecurityCode(rs.getInt("SECURITYCODE"));
				card.setCustomerId(rs.getInt("CUSTOMERID"));
				card.setExpireMonth(rs.getInt("expireMonth"));
				card.setExpireYear(rs.getInt("expireYear"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();

			} catch (SQLException e) {
				System.err.println("A error occurs when attempt close resources!!!");
				e.printStackTrace();
			}

			super.closeConnection();
		}

		return card;
	}

	@Override
	public int insertCard(CustomerCard cust) {
	//	CustomerCard card = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int keyGenerated = 0;

		try {
			super.getConnection();
		//	ps = conn.prepareStatement(SQL.INSERT_CARD.getQuery(), Statement.RETURN_GENERATED_KEYS);
			ps = conn.prepareStatement(SQL.INSERT_CARD.getQuery(), new String[] {"CARDID"});
			// CARDNUMBER, HOLDERNAME, EXPIREDATE, SECURITYCODE, CUSTOMERID
			ps.setString(1, cust.getCardNumber());
			ps.setString(2, cust.getHolderName());
			ps.setDate(3, cust.getExpireDate());
			ps.setInt(4, cust.getSecurityCode());
			ps.setInt(5, cust.getCustomerId());

			ps.executeQuery();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				keyGenerated = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();

			} catch (SQLException e) {
				System.err.println("A error occurs when attempt close resources!!!");
				e.printStackTrace();
			}

			super.closeConnection();
		}
		return keyGenerated;

	}

	@Override
	public boolean updateCard(CustomerCard cust) {
		PreparedStatement ps = null;
		boolean isUpdated = false;

		try {
			super.getConnection();
			ps = conn.prepareStatement(SQL.UPDATE_CARD.getQuery());
			// UPDATE CUSTOMER_CARD SET CARDNUMBER=? , HOLDERNAME=?, EXPIREDATE=?,
			// SECURITYCODE=?, CUSTOMERID=? WHERE CARDID=?
			ps.setString(1, cust.getCardNumber());
			ps.setString(2, cust.getHolderName());
			ps.setDate(3, cust.getExpireDate());
			ps.setInt(4, cust.getSecurityCode());
			ps.setInt(5, cust.getCustomerId());
			ps.setInt(5, cust.getCardId());

			isUpdated = ps.executeUpdate() > 0 ? true : false;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();

			} catch (SQLException e) {
				System.err.println("A error occurs when attempt close resources!!!");
				e.printStackTrace();
			}

			super.closeConnection();
		}
		return isUpdated;

	}

	@Override
	public boolean deleteCard(int cardId) {
		PreparedStatement ps = null;
		boolean isDeleted = false;

		try {
			super.getConnection();
			ps = conn.prepareStatement(SQL.DELETE_CARD.getQuery());
			// UPDATE CUSTOMER_CARD SET CARDNUMBER=? , HOLDERNAME=?, EXPIREDATE=?,
			// SECURITYCODE=?, CUSTOMERID=? WHERE CARDID=?

			ps.setInt(1, cardId);

			isDeleted = ps.executeUpdate() > 0 ? true : false;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();

			} catch (SQLException e) {
				System.err.println("A error occurs when attempt close resources!!!");
				e.printStackTrace();
			}

			super.closeConnection();
		}
		return isDeleted;

	}

	@Override
	public CustomerCard findCard(String cardNumber, int securityCode, int expireMonth, int expireYear ) {
		CustomerCard card = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		if(expireMonth>12 || expireMonth<1 || expireYear<2000)
			return null;
		try {
			super.getConnection();
			ps = conn.prepareStatement(SQL.FINDCARD.getQuery());
			ps.setString(1, cardNumber);
			LocalDate expireDate = YearMonth.of(expireYear, expireMonth)
								.atEndOfMonth();
			ps.setDate(2, Date.valueOf(expireDate));
			ps.setInt(3, securityCode);

			rs = ps.executeQuery();
			if (rs.next()) {
				card = new CustomerCard();
				// CARDID, CARDNUMBER, HOLDERNAME, EXPIREDATE, SECURITYCODE, CUSTOMERID
				card.setCardId(rs.getInt("CARDID"));
				card.setCardNumber(rs.getString("CARDNUMBER"));
				card.setHolderName(rs.getString("HOLDERNAME"));
				card.setExpireDate(rs.getDate("EXPIREDATE"));
				card.setSecurityCode(rs.getInt("SECURITYCODE"));
				card.setCustomerId(rs.getInt("CUSTOMERID"));
				card.setExpireMonth(rs.getInt("expireMonth"));
				card.setExpireYear(rs.getInt("expireYear"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();

			} catch (SQLException e) {
				System.err.println("A error occurs when attempt close resources!!!");
				e.printStackTrace();
			}

			super.closeConnection();
		}

		return card;

	}

}
