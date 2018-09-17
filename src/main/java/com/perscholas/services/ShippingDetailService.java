package com.perscholas.services;


import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.perscholas.dao.AbstractDAO;
import com.perscholas.dao.ShippingDetailDAO;
import com.perscholas.model.ShippingDetail;

@Service

public class ShippingDetailService extends AbstractDAO implements ShippingDetailDAO {
	static Logger log = Logger.getLogger(ShippingDetailService.class);

	@Override
	public List<ShippingDetail> getAllByOrderId(int orderId) {
		List<ShippingDetail> list=new ArrayList<>();
		PreparedStatement ps= null;
		ResultSet rs =null;
		
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_BY_ORDER_ID.getQuery());
			ps.setInt(1, orderId);
			rs = ps.executeQuery();
			//select s.id, s.ORDERDETAILID, s.barcode, s.RETURN_DATE
			while(rs.next()) {
				ShippingDetail temp = new ShippingDetail();
				//id, first_name, last_name, address, city, state, email
				temp.setId(rs.getInt(1));
				temp.setOrderid(rs.getInt(2));
				temp.setBarcode(rs.getString(3));
				temp.setReturnDate(rs.getDate(4));
				list.add(temp);
			}
		} catch (SQLException e) {
			log.error(String.format("Error at getAllByOrderId %s" , e.getMessage() ));

		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at getAllByOrderId %s" , e.getMessage() ));

			}
			
			super.closeConnection();
		}
		
		
		return list;
	}

	@Override
	public boolean insertByOrderId(int orderId) {
		CallableStatement cs= null;
		boolean isInsert = false;
		
		try {
			super.getConnection();
			cs= conn.prepareCall(SQL.INSERT_BY_ORDER_ID.getQuery());
			cs.setInt(1, orderId);
			isInsert = cs.execute();
			
			
		} catch (SQLException e) {
			log.error(String.format("Error at insertByOrderId %s" , e.getMessage() ));

		}finally {
			try {
				
				if(cs!=null)	cs.close();
				
			}catch(SQLException e) {
				log.error(String.format("Error at insertByOrderId %s" , e.getMessage() ));

			}
			
			super.closeConnection();
		}
		
		
		return isInsert;
	}

	@Override
	public boolean insertByOrderDetailId(int orderDetailId) throws SQLException{
		CallableStatement cs= null;
		boolean isInsert = false;
		
		try {
			super.getConnection();
			cs= conn.prepareCall(SQL.INSERT_BY_ORDER_DETAIL_ID.getQuery());
			cs.setInt(1, orderDetailId);
			isInsert = cs.execute();
			
			
		} finally {
			if(cs!=null)	cs.close();
			
			//super.closeConnection();
		}
		
		
		return isInsert;
	}

}
