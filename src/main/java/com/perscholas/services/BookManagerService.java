package com.perscholas.services;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.perscholas.dao.AbstractDAO;
import com.perscholas.dao.BookDAO;
import com.perscholas.dao.BookManagerDAO;
import com.perscholas.dao.PriceHistoryDAO;
import com.perscholas.exception.InsertBookException;
import com.perscholas.exception.StoreFileException;
import com.perscholas.exception.UpdateBookException;
import com.perscholas.model.Book;
import com.perscholas.model.BookForm;

@Service

public class BookManagerService extends AbstractDAO implements BookManagerDAO {
	
	static Logger log = Logger.getLogger(BookManagerService.class);
	
	@Autowired
	BookDAO bookDAO;
	
	@Autowired
	PriceHistoryDAO priceDAO;

	@Override
	@Transactional(rollbackFor= {IOException.class, SQLException.class, StoreFileException.class, UpdateBookException.class, InsertBookException.class})
	
	public boolean saveBook(BookForm bookForm) throws IOException, SQLException, StoreFileException, UpdateBookException, InsertBookException {
		log.error("Transaction is active? " + TransactionSynchronizationManager.isActualTransactionActive());
		
		String action = bookForm.getAction();
		
		Book book = bookForm.getBook();

		// upload and add new book is do the same step
		if (!bookForm.getFile().isEmpty()) {
			//1.upload file to server
			log.info("//1.upload file to server");
			Utils utils = new Utils();
			
			
			//utils write when it upload file successful, it return file name, else it return "FAIL"
			String messageUpload = utils.writeFile(bookForm.getFile(), book.getISBN_13());
			
			
			if (messageUpload.equals("Fail")) {

				log.error("Upload file got error");
				throw new StoreFileException(bookForm.getFile().getOriginalFilename());
			}
		
			//set file name from messageUpload file to book
			book.setHardCoverPath(messageUpload);
			log.info("Book Information" + book);
		}
		
		
		//2.1 insert to database
		if(action=="insert") {
			// insert book to book table
			log.info("2.1 insert to database");
			int keyGenerate=bookDAO.insertBook(book) ;
			if (keyGenerate> 0) {
				log.info("2.1 insert to database successfull");
				
			} else {// can't insert to database
				log.error("2.1 insert to database fail");
				throw new InsertBookException();
				
			}
	
		//2.2 else if action==update
		}else {
			// update book to book table
			log.info("UPDATE TO BOOK TABLE" );
			if(bookDAO.updateBook(book)) {
				log.info("Update sucessfull");
				
			}
			else {
				log.error("Update book FAIL at updateBook");
				throw new UpdateBookException();
			}
		
			
		}
		log.info("Insert to price history. Call SavePriceHistory");
		
		//3 Insert or update to price history
		
		if(!priceDAO.insertUpdatePrice(book.getId(), bookForm.getInvoicePrice(), book.getPrice()))
			
			
			throw new SQLException("Update price history is not successful");

		
		return false;
	}

}
