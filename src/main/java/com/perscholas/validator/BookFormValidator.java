package com.perscholas.validator;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.perscholas.dao.BookDAO;
import com.perscholas.dao.PriceHistoryDAO;
import com.perscholas.model.Book;
import com.perscholas.model.BookForm;

@Component
public class BookFormValidator implements Validator{
	
	 private static Logger log = Logger.getLogger(CustomerValidator.class);
	 private static final Pattern ISBN_10 =
	            Pattern.compile("[0-9A-Z\\-]{10}");
	 private static final Pattern ISBN_13 =
	            Pattern.compile("([0-9]{3}[\\-][0-9]{10})|([A-Z0-9]{10})");
	 
	 

	@Autowired
	BookDAO bookDAO;
	
	@Autowired
	PriceHistoryDAO priceDAO;
	
	@Override
	public boolean supports(Class<?> className) {
	
		return BookForm.class.equals(className);
	}

	@Override
	public void validate(Object target, Errors errors) {
		BookForm bookForm =(BookForm) target;
		Book book = bookForm.getBook();
		log.info("VALIDATOR BOOKFORM IS CALL");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "book.name","bF.book.name.empty" );
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "book.price","bF.book.price.empty" );
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "invoicePrice","bF.invoicePrice.empty" );
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "book.authorId","bF.book.authorId.empty" );
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "book.publisherId","bF.book.publisherId.empty" );
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "book.ISBN_13","bF.book.ISBN_13.empty" );
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "book.categoryId","bF.book.categoryId.empty" );
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "book.description","bF.book.description.empty" );
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "book.pages","bF.book.pages.empty" );

		if(book.getPages()<1)
			errors.rejectValue("book.pages","bF.book.pages.pattern" );
		if(book.getPrice()<bookForm.getInvoicePrice()) {
			errors.rejectValue("book.price","bF.book.price.pattern" );
			errors.rejectValue("invoicePrice","bF.invoicePrice.pattern" );
			
		}
		if(!ISBN_10.matcher(book.getISBN_10()).matches()) {
			errors.rejectValue("book.ISBN_10","bF.book.ISBN_10.pattern" );
		}
		if(!ISBN_13.matcher(book.getISBN_13()).matches()) {
			errors.rejectValue("book.ISBN_13","bF.book.ISBN_13.pattern" );
		}
		if (bookForm.getAction().equals("insert")) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "file","bF.file.empty" );
		}
	}

}
