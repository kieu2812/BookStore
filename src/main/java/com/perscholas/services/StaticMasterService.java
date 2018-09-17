package com.perscholas.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.perscholas.dao.AbstractDAO;
import com.perscholas.dao.StaticMasterDAO;
import com.perscholas.model.StaticMaster;

@Service
public class StaticMasterService extends AbstractDAO implements StaticMasterDAO {

	static Logger log = Logger.getLogger(StaticMasterService.class);
	@Override
	public List<StaticMaster> getAllStates() {
		

		List<StaticMaster> list=new ArrayList<>();
		PreparedStatement ps= null;
		ResultSet rs =null;
		
		try {
			super.getConnection();
			ps= conn.prepareStatement(SQL.GET_STATES.getQuery());
		
			rs = ps.executeQuery();
		//select function_type,code, description, name
			while(rs.next()) {
				StaticMaster temp = new StaticMaster();
				//id, first_name, last_name, address, city, state, email
				temp.setCode(rs.getString(1));
				temp.setName(rs.getString(2));
				list.add(temp);
			}
		} catch (SQLException e) {
			System.err.println("A error occurs when attempt get resources!!! in StaticMasterService.getState");
		}finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				
			}catch(SQLException e) {
				System.err.println("A error occurs when attempt close resources!!!  in StaticMasterService.getState");
				e.printStackTrace();
			}
			
			super.closeConnection();
		}
		
		
		return list;
	}

}
