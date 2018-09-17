package com.perscholas.exception;

public class StoreFileException extends Exception {

	  
		/**
	 * Use when store file image and have errors 
	 */
	private static final long serialVersionUID = 1L;
		public StoreFileException(){
	        super();
	    }
	    public StoreFileException(String fileName){
	        super("Can't store file upload. Please check your " + fileName);
	       
	    }

}
