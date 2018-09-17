package com.perscholas.dao;

import java.util.List;

import com.perscholas.model.BookInfo;
import com.perscholas.model.PaginationResult;

public interface BookInfoDAO {
	enum SQL{
		
		GET_ALL_BOOKS("SELECT B.ID, B.NAME, HARDCOVERPATH, PRICE,NVL(QTYINSTOCK,0)," + 
				"         NVL(R.AVGRATING,0), TOTALREVIEW       " + 
				" From BOOK B " + 
				" LEFT JOIN (SELECT BOOKID, ROUND(AVG(RATING),1)  AS AVGRATING , COUNT(BOOKID) AS TOTALREVIEW " + 
				"        FROM REVIEW R " + 
				"        GROUP BY BOOKID " + 
				"    ) R ON R.BOOKID = B.ID "+
				" WHERE DELETEFLAG is null or B.DELETEFLAG!=1 "),
		GET_BOOK_BY_ID("SELECT B.ID, B.NAME, HARDCOVERPATH, PRICE,NVL(QTYINSTOCK,0)," + 
				"         NVL(R.AVGRATING,0), TOTALREVIEW       " + 
				" From BOOK B " + 
				" LEFT JOIN (SELECT BOOKID, ROUND(AVG(RATING),1)  AS AVGRATING , COUNT(BOOKID) AS TOTALREVIEW " + 
				"        FROM REVIEW R " + 
				"        GROUP BY BOOKID " + 
				"    ) R ON R.BOOKID = B.ID "+ 
				" WHERE B.ID= ? AND (DELETEFLAG is null or B.DELETEFLAG!=1)  "),

		
		FIND_BOOK("SELECT B.ID, B.NAME, HARDCOVERPATH, PRICE, QTYINSTOCK, " + 
				"         NVL(R.AVGRATING,0),NVL(TOTALREVIEW,0)     " + 
				"From BOOK B  " + 
				"INNER JOIN CATEGORY C ON C.ID= B.CATEGORYID " + 
				"LEFT JOIN (SELECT BOOKID, ROUND(AVG(RATING),1) AS AVGRATING, COUNT(BOOKID) AS TOTALREVIEW " + 
				"        FROM REVIEW R  " + 
				"        GROUP BY BOOKID  " + 
				"    ) R ON R.BOOKID = B.ID " + 			
				"WHERE ( trim(UPPER(B.NAME)) LIKE ? OR trim(UPPER(C.NAME))  LIKE ?  OR B.ISBN_10=? OR B.ISBN_13=?) AND (B.DELETEFLAG is null or B.DELETEFLAG!=1 ) "),
		GET_BOOK_BY_CATEGORY_ID("SELECT B.ID, B.NAME, HARDCOVERPATH, PRICE, QTYINSTOCK, " + 
				"         NVL(R.AVGRATING,0),NVL(TOTALREVIEW,0)     " + 
				"From BOOK B  " + 
				"INNER JOIN CATEGORY C ON C.ID= B.CATEGORYID " + 
				"LEFT JOIN (SELECT BOOKID, ROUND(AVG(RATING),1) AS AVGRATING, COUNT(BOOKID) AS TOTALREVIEW " + 
				"        FROM REVIEW R  " + 
				"        GROUP BY BOOKID  " + 
				"    ) R ON R.BOOKID = B.ID " + 			
				"WHERE (c.id= ? ) AND (B.DELETEFLAG is null or B.DELETEFLAG!=1 ) "),
		
		GET_BEST_SELLER(
				"WITH OD AS("   +    
				" SELECT * FROM ( " +
				"      SELECT BOOKID, SUM(QUANTITY) TOTAL " +   
				"      FROM ORDER_DETAIL " +
				"      GROUP BY BOOKID " +
				"       ORDER BY SUM(QUANTITY)  DESC " +
				"     ) " +
				"  WHERE ROWNUM<=20 " +   
				" )      " +
				" SELECT B.ID, B.NAME, HARDCOVERPATH, PRICE,NVL(QTYINSTOCK,0), " +
				" 			        NVL(R.AVGRATING,0), TOTALREVIEW, OD.TOTAL   " +
				" 				From BOOK B INNER JOIN OD ON OD.BOOKID= B.ID       " +
				" 				 LEFT JOIN (SELECT BOOKID, ROUND(AVG(RATING),1)  AS AVGRATING , COUNT(BOOKID) AS TOTALREVIEW " + 
				" 				        FROM REVIEW R   " +
				" 				        GROUP BY BOOKID  " +
				" 			    ) R ON R.BOOKID = B.ID " +
				          
				" 			WHERE (DELETEFLAG is null or B.DELETEFLAG!=1 )" 
      );
		
		
		private final String query;
		private SQL(String s) {
			this.query= s;
			
		}
		public String getQuery() {
			return this.query;
		}
		
	}

	public List<BookInfo> getAllBooks();
	public List<BookInfo> getBestSellerBooks();
	public BookInfo getByBookId(int bookid);
	public List<BookInfo> getByCategoryId(int categoryId);
	public List<BookInfo> findBook(String keyword);
	
	public PaginationResult<BookInfo> getAllBooksPagination(int page, int maxResult, int maxNavigationPage);
}
