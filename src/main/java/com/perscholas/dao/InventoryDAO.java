package com.perscholas.dao;

import java.util.List;

import com.perscholas.model.Inventory;

public interface InventoryDAO {
	enum SQL{
		GET_ALL_ITEMS_INSTOCK("SELECT BARCODE, BOOKID FROM INVENTORY WHERE ISAVAILABLE=1 ORDER BY BOOKID"),
		GET_ITEMS_BY_BOOKID("SELECT * FROM INVENTORY WHERE BOOKID=? AND ISAVAILABLE=1"),
		CHECK_VALID_ITEM_BY_BARCODE("SELECT count(*) FROM INVENTORY WHERE BARCODE=?"),
		UPDATE_ITEM_ISAVAILABLE("UPDATE INVENTORY SET ISAVAILABLE=? WHERE BARCODE=?"),
		INSERT_ITEM("INSERT INTO INVENTORY(BARCODE,BOOKID,ISAVAILABLE) VALUES(?,?,1)"),
		DELETE_ITEM("DELETE FROM INVENTORY WHERE BARCODE=?");
		private final String query;
		private SQL(String s){
			this.query= s;
			
		}
		public String getQuery() {
			return this.query;
		}
	}
	public List<Inventory> getAllItemsInStock();
	public List<Inventory> getItemsByBookID(int bookId);
	public boolean isValidItem(String barcode);
	public boolean updateaItemAvailable(boolean isAvailable, String barcode);
	public boolean insertItem(String barcode, int bookId);
	public boolean deleteItem(String barcode);
	
	
}
