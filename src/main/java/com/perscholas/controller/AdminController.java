package com.perscholas.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.perscholas.dao.BookDAO;
import com.perscholas.dao.BookInfoDAO;
import com.perscholas.dao.BookManagerDAO;
import com.perscholas.dao.CategoryDAO;
import com.perscholas.dao.OrderReportDAO;
import com.perscholas.dao.PriceHistoryDAO;
import com.perscholas.dao.PublisherDAO;
import com.perscholas.model.Book;
import com.perscholas.model.BookForm;
import com.perscholas.model.BookInfo;
import com.perscholas.model.Category;
import com.perscholas.model.OrderReport;
import com.perscholas.model.PaginationResult;
import com.perscholas.model.PriceHistory;
import com.perscholas.validator.BookFormValidator;

@Controller
@MultipartConfig
@RequestMapping("/admin")
public class AdminController {
	
	static Logger log = Logger.getLogger(AdminController.class);
	
	 static final int MAX_RESULT = 5;
	 static final  int MAX_NAVIGATION_PAGE = 5;
	
	@Autowired
	BookDAO bookDAO;
	@Autowired
	PublisherDAO pubDAO;
	
	@Autowired 
	CategoryDAO catDAO;
	
	@Autowired
	BookInfoDAO bookInfoDAO;
	
	@Autowired
	PriceHistoryDAO priceDAO;
	
	@Autowired
	BookFormValidator bookFormValidator;

	@Autowired
	OrderReportDAO orderReportDAO;
	
	@Autowired
	BookManagerDAO bookManagerDAO;

/**
 * This method use for add new book to database
 * 
 * @return
 */
	@GetMapping("/book")
	public ModelAndView showBookManagement(@RequestParam(value="page", defaultValue="1") int page) {
		ModelAndView mav = new ModelAndView("bookManagement");
		PaginationResult<BookInfo> result = bookInfoDAO.getAllBooksPagination(page, MAX_RESULT, MAX_NAVIGATION_PAGE);	
		mav.addObject("paginationBooks", result);
		return mav;
	}

	
	@GetMapping("/book/showBookForm")
	public ModelAndView showFormAddBook() {

		ModelAndView mav = new ModelAndView("addBook");
		Map<Integer, String> publishers = pubDAO.getAllNames();

		List<Category> categories = catDAO.getAllCategories();
		Map<Integer, String> mapCat = categories.stream().collect(Collectors.toMap(Category::getId, Category::getName));
		mav.addObject("publisherList", publishers);
		mav.addObject("categoryList", mapCat);

		mav.addObject("bookForm", new BookForm("insert"));
		return mav;

	}
	
	@GetMapping("/book/updateBook")
	public ModelAndView showToUpdateBook(HttpServletRequest request, @RequestParam("bookId") int bookId) {

		ModelAndView mav = new ModelAndView("addBook");
		
		Book book = bookDAO.getBookById(bookId);
		BookForm bookForm = new BookForm("update");
		bookForm.setBook(book);
		log.info("URL /book/updateBook is call");
	
		PriceHistory priceHist =priceDAO.getCurrentPriceById(bookId);
		if (priceHist != null) {
			bookForm.setInvoicePrice(priceHist.getInvoicePrice());
			
		}		

		Map<Integer, String> publishers = pubDAO.getAllNames();
		
		List<Category> categories = catDAO.getAllCategories();
		Map<Integer, String> mapCat = categories.stream().collect(Collectors.toMap(Category::getId, Category::getName));
		mav.addObject("publisherList", publishers);
		mav.addObject("categoryList", mapCat);

		mav.addObject("bookForm", bookForm);
		return mav;

	}
	
	@RequestMapping(value="/book/updateProcess", method = RequestMethod.POST)
	public String addBook(@ModelAttribute("bookForm") @Validated BookForm bookForm, 			
			BindingResult result, Model model, HttpServletRequest request){
		
		 String action = bookForm.getAction();
		 bookFormValidator.validate(bookForm, result);
		log.info("URL /book/updateProcess action: " + action);
		log.info("URL /book/updateProcess invoicePrice: " + bookForm.getInvoicePrice());
		log.info("URL /book/updateProcess bookName: " + bookForm.getBook().getName());
		log.info("Is Empty FILE UPLOAD= " + bookForm.getFile().getOriginalFilename());
		
		Map<Integer, String> publishers = pubDAO.getAllNames();
		model.addAttribute("publisherList", publishers);
		
		List<Category> categories = catDAO.getAllCategories();
		Map<Integer, String> mapCat = categories.stream().collect(Collectors.toMap(Category::getId, Category::getName));
		model.addAttribute("categoryList", mapCat);
		
		log.info("Got some errors when binding and validator data");
		log.info("URL /book/updateProcess is call.");
		 if (result.hasErrors()) {

			return "addBook";
		 }
		 
			// check valid if action la insert book
			Book book = bookForm.getBook();
			if(action=="insert") {
				log.info("// check exists one book with this name in the database");
				// check exists one book with this name in the database
				if(bookDAO.getBookByName(book.getName())!=null) {
					result.rejectValue("book.name","bF.book.name.duplicate" );
					return  "addBook";
					
				}
				//check exists one book with ISBN13 in database or not
				log.info("//check exists one book with ISBN13 in database or not");
				if(bookDAO.getBookByIsbn13(book.getISBN_13())!=null) {
					
					result.rejectValue("book.ISBN_13","bF.book.ISBN_13.duplicate" );
					 return "addBook";
				}
			}
			
			try {
				bookManagerDAO.saveBook(bookForm);
			}catch(Exception e){
				log.error(e.getMessage());
				model.addAttribute("error", e.getMessage());
				return "addBook";
			}
		
		//save Book succesfull 
			model.addAttribute("message", "Book is saved in the database succesful");
		return "success";
	}

	@PostMapping("/book/searchBook")
	public ModelAndView searchBook(@RequestParam("searchWord") String searchWord, 
			 @RequestParam(value="page", defaultValue="1") int page	) {
		List<BookInfo> listBook = null;
		String message = "";
		PaginationResult<BookInfo> result=null;
		log.info("**********************Search word = " + searchWord);
		if (!searchWord.isEmpty()) {
			listBook = bookInfoDAO.findBook(searchWord);
			result = new PaginationResult<>(listBook, page, MAX_RESULT, MAX_NAVIGATION_PAGE);
		}
		ModelAndView mav = new ModelAndView("bookManager");
		if (listBook != null && listBook.isEmpty()) {
			message = "Don't find any book in our store";

		}
		log.info(message);
		mav.addObject("message", message);
		mav.addObject("paginationBooks", result);
		return mav;
	}
	@GetMapping("/order/monthly")
	public ModelAndView showFormOrderMonthly() {

		return new ModelAndView("viewOrderMonthly");
	

	}
	@PostMapping("/order/monthly")
	public String getMonthly(@RequestParam("month") int month, @RequestParam("year") int year, 
			 @RequestParam(value="page", defaultValue="1") int page	, Model model) {
		log.info("****************URL /order/monthly/");
		PaginationResult<OrderReport> result=null;
		String message=null;
		log.info("month=" + month);
		log.info("year=" + year);
		log.info("page=" + page);
		if((month<1 && month>12 )||(year>LocalDate.now().getYear())) {
			model.addAttribute("errors","Month or year is invalid");
			return "viewOrderMonthly";
		}
			
		List<OrderReport> orderReports = orderReportDAO.getAllByMonthAndYear(month, year);
		if (orderReports != null && orderReports.isEmpty()) {
			message = "Don't find any book in our store";
			model.addAttribute("errors", message);
			return "viewOrderMonthly";
		}
		log.info("Message==============>" + message);
		result= new PaginationResult<>(orderReports, page, MAX_RESULT, MAX_NAVIGATION_PAGE);
		model.addAttribute("paginationOrders", result);
		
		return "viewOrderMonthly";

	}
/*
	@GetMapping("/order/monthly/{month}/{year}")
	public String getMonthly(@PathVariable("month") int month, @PathVariable("year") int year, 
			 @RequestParam(value="page", defaultValue="1") int page	, Model model) {
		log.info("****************URL /order/monthly/{month}/{year}");
		PaginationResult<OrderReport> result=null;
		String message=null;
		log.info("month=" + month);
		log.info("year=" + year);
		log.info("page=" + page);
		if((month<1 && month>12 )||(year>LocalDate.now().getYear())) {
			model.addAttribute("errors","Month or year is invalid");
			return "viewOrderMonthly";
		}
			
		List<OrderReport> orderReports = orderReportDAO.getAllByMonthAndYear(month, year);
		if (orderReports != null && orderReports.isEmpty()) {
			message = "Don't find any book in our store";
			model.addAttribute("errors", message);
			return "viewOrderMonthly";
		}
		log.info("Message==============>" + message);
		result= new PaginationResult<OrderReport>(orderReports, page, MAX_RESULT, MAX_NAVIGATION_PAGE);
		model.addAttribute("paginationOrders", result);
		
		return "viewOrderMonthly";

	}
*/
	@GetMapping("/order/daily")
	public ModelAndView showFormOrderDaily() {

		return new ModelAndView("viewOrderDaily");
		

	}
	@PostMapping("/order/daily")
	public String getOrderDaily(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date, @RequestParam(value="page", defaultValue="1") int page	, Model model) {
		log.info("****************URL /order/daily/");
		PaginationResult<OrderReport> result=null;
		String message=null;
		LocalDate localDate= date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		log.info("month=" + localDate.getMonthValue());
		log.info("year=" + localDate.getYear());
		log.info("day=" + localDate.getDayOfMonth());
		log.info("page="+ page);
		if(localDate.isAfter(LocalDate.now())) {
			model.addAttribute("errors","Report date must be less then current day.");
			return "viewOrderDaily";
		}
			
		localDate= localDate.plusDays(1);
		List<OrderReport> orderReports = orderReportDAO.getAllByDate(localDate);
		if (orderReports != null && orderReports.isEmpty()) {
			message = "Don't find any order in " + localDate.toString();
			model.addAttribute("errors", message);
			return "viewOrderDaily";
		}
		log.info("Message==============>" + message);
		result= new PaginationResult<>(orderReports, page, MAX_RESULT, MAX_NAVIGATION_PAGE);
		model.addAttribute("paginationOrders", result);
		model.addAttribute("date", localDate);
		
		return "viewOrderDaily";

	}

}
