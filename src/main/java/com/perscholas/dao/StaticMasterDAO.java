package com.perscholas.dao;

import java.util.List;

import com.perscholas.model.StaticMaster;

public interface StaticMasterDAO {
	
	
	enum SQL{
		GET_STATES("select code,  name from static_master where function_type='STATE'"); 
				
		
		private final String query;
		private SQL(String s){
			this.query= s;
			
		}
		public String getQuery() {
			return this.query;
		}
	}
	public List<StaticMaster> getAllStates();

}
