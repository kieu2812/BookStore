package com.perscholas.exception;

public class OutOfStockException extends Exception {

	  
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		public OutOfStockException(){
	        super();
	    }
	    public OutOfStockException(String s, String bookName, int actualQuantity, int requestQuantity){
	        super("Sorry, book name "+ bookName+ " is  "+ s + ". Actual Quantity: " + actualQuantity + ". Request Quantity: " +  requestQuantity);
	       
	    }

}
