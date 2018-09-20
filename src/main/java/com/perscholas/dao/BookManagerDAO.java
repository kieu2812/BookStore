package com.perscholas.dao;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.transaction.annotation.Transactional;

import com.perscholas.exception.InsertBookException;
import com.perscholas.exception.StoreFileException;
import com.perscholas.exception.UpdateBookException;
import com.perscholas.model.BookForm;

public interface BookManagerDAO  {

	public boolean saveBook(BookForm bookForm) throws IOException, SQLException, StoreFileException, UpdateBookException, InsertBookException ;

}
