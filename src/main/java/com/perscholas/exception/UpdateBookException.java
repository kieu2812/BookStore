package com.perscholas.exception;

public class UpdateBookException extends Exception {
	private static final long serialVersionUID = 1L;
	public UpdateBookException(){
        super("Update book fail");
    }
}
