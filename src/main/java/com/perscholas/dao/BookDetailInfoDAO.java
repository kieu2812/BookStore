package com.perscholas.dao;

import com.perscholas.model.BookDetailInfo;

public interface BookDetailInfoDAO {
	enum SQL{
		GET_BOOK_BY_ID("SELECT B.ID, B.NAME, HARDCOVERPATH, PRICE, A.NAME AS AUTHOR, ISBN_10, ISBN_13, P.NAME AS PUBLISHER, DESCRIPTION, PAGE, QTYINSTOCK, " + 
				"         NVL(R.AVGRATING,0), TOTALREVIEW        " + 
				"From BOOK B INNER JOIN PUBLISHER P ON B.PUBLISHERID= P.ID " + 
				"INNER JOIN BOOK_AUTHOR BA ON BA.BOOKID= B.ID " + 
				"INNER JOIN AUTHOR A ON A.ID= BA.AUTHORID " + 
				"INNER JOIN CATEGORY C ON c.id= b.categoryid " + 
				"LEFT JOIN (SELECT BOOKID, ROUND(AVG(RATING),1) AS AVGRATING, COUNT(BOOKID) AS TOTALREVIEW " + 
				"        FROM REVIEW R " + 
				"        GROUP BY BOOKID " + 
				"    ) R ON R.BOOKID = B.ID " + 
				
				"WHERE B.ID=?");
		private final String query;
		private SQL(String s) {
			this.query= s;
			
		}
		public String getQuery() {
			return this.query;
		}
		
	}

	public BookDetailInfo getBookById(int bookid);
	
}
