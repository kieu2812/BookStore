package com.perscholas.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.perscholas.dao.BookDAO;
import com.perscholas.dao.BookDetailInfoDAO;
import com.perscholas.dao.BookInfoDAO;
import com.perscholas.dao.CustomerCardDAO;
import com.perscholas.dao.CustomerDAO;
import com.perscholas.dao.OrderDetailDAO;
import com.perscholas.dao.OrderDetailInfoDAO;
import com.perscholas.dao.OrderInfoDAO;
import com.perscholas.dao.OrderManagerDAO;
import com.perscholas.dao.ReviewDAO;
import com.perscholas.dao.ShippingDetailDAO;
import com.perscholas.dao.StaticMasterDAO;
import com.perscholas.dao.UserRoleDAO;
import com.perscholas.exception.OutOfStockException;
import com.perscholas.model.BookDetailInfo;
import com.perscholas.model.BookInfo;
import com.perscholas.model.ContactUs;
import com.perscholas.model.Customer;
import com.perscholas.model.CustomerCard;
import com.perscholas.model.FormChangePassword;
import com.perscholas.model.OrderDetailInfo;
import com.perscholas.model.OrderInfo;
import com.perscholas.model.PaginationResult;
import com.perscholas.model.Review;
import com.perscholas.model.ShippingAddress;
import com.perscholas.model.ShoppingCart;
import com.perscholas.model.ShoppingCartIem;
import com.perscholas.model.StaticMaster;
import com.perscholas.model.UserRole;
import com.perscholas.services.MailService;
import com.perscholas.services.ShoppingCartService;
import com.perscholas.services.Utils;
import com.perscholas.validator.FormChangePassValidator;
import com.perscholas.validator.ShippingAddressValidator;

@Controller
public class HomeController {
	static Logger log = Logger.getLogger(HomeController.class);

	private static final int MAX_RESULT = 5;
	private static final int MAX_NAVIGATION_PAGE = 5;

	@Autowired
	OrderInfoDAO orderInfoDAO;

	@Autowired
	BookInfoDAO bookInfoDAO;

	@Autowired
	BookDAO bookDAO;

	@Autowired
	CustomerDAO customerDAO;

	@Autowired
	CustomerCardDAO customerCardDAO;

	@Autowired
	OrderDetailDAO orderDetailDao;
	@Autowired
	OrderDetailInfoDAO orderDetailInfoDAO;
	@Autowired
	ShippingDetailDAO shipDetailDAO;

	@Autowired
	ShippingAddressValidator shippingValidator;

	@Autowired
	BookDetailInfoDAO detailInfoDAO;
	
	@Autowired
	StaticMasterDAO staticDAO;

	@Autowired
	OrderManagerDAO orderManagerDAO;

	@Autowired
	UserRoleDAO userRoleDAO;

	@Autowired
	FormChangePassValidator formChangePassValidator;

	@Autowired
	MailService mailService;

	@Autowired
	ReviewDAO reviewDAO;

	@GetMapping("/")
	public ModelAndView goHomePage(HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		ModelAndView mav = new ModelAndView("welcome");
		List<BookInfo> bestSeller = bookInfoDAO.getBestSellerBooks();
		PaginationResult<BookInfo> result = new PaginationResult<>(bestSeller, page, MAX_RESULT, MAX_NAVIGATION_PAGE);
		mav.addObject("paginationBooks", result);

		return mav;
	}


	// *************************LOGIN SPRING SECURITY**********************
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request,
			SessionStatus status) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

		if (logout != null) {

			// attributes will be removed once the handler indicates completion of its
			// conversational session.
			status.setComplete();
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login_SECURITY");

		return model;

	}

	// customize the error message
	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession().getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "Invalid username and password!";
		}

		return error;
	}

	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		log.debug("URL 403 gets called");

		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			log.info(userDetail);
			model.addObject("username", userDetail.getUsername());
		}

		log.debug("Forward to Page 403");

		model.setViewName("403");
		return model;

	}


	/**** Book Controller *****/
	@GetMapping("/book/list")
	public ModelAndView getBooks(@RequestParam(value = "page", defaultValue = "1") int page) {
		PaginationResult<BookInfo> result = bookInfoDAO.getAllBooksPagination(page, MAX_RESULT, MAX_NAVIGATION_PAGE);
		ModelAndView mav = new ModelAndView("listBook");
		mav.addObject("paginationBooks", result);
		return mav;
	}

	@GetMapping("/book/getBook")
	public ModelAndView getBook(HttpServletRequest request, @RequestParam("bookId") int id) {

		Integer isBought = 0;
		BookDetailInfo bookDetail = detailInfoDAO.getBookById(id);

		ModelAndView mav = new ModelAndView("bookDetail");
		mav.addObject("bookDetail", bookDetail);
		List<Review> reviews = reviewDAO.getAllReviewsByBookId(id);

		Integer customerId = (Integer) Utils.getCustIdFromSession(request);
		mav.addObject("custId", customerId);
		if (customerId != null && orderInfoDAO.checkCustBuyBook(customerId, id)) {
			isBought = 1;

			mav.addObject("isBought", isBought);
		}
		log.info("Is Bought this book= " + isBought);

		mav.addObject("reviews", reviews);

		return mav;
	}

	@PostMapping("/book/searchBook")
	public ModelAndView searchBook(@RequestParam("searchWord") String searchWord,
			@RequestParam(value = "page", defaultValue = "1") int page) {

		List<BookInfo> listBook = null;
		String message = "";
		PaginationResult<BookInfo> result = null;
		log.info("**********************Search word = " + searchWord);
		if (!searchWord.isEmpty()) {
			listBook = bookInfoDAO.findBook(searchWord);
			result = new PaginationResult<>(listBook, page, MAX_RESULT, MAX_NAVIGATION_PAGE);
		}
		ModelAndView mav = new ModelAndView("listBook");
		if (listBook != null && listBook.isEmpty()) {
			message = "Don't find any book in our store";

		}
		log.info("Message==============>" + message);
		mav.addObject("message", message);
		mav.addObject("paginationBooks", result);
		return mav;
	}

	@GetMapping("/book/category/{id}")
	public ModelAndView getBookByCategoryId(@PathVariable("id") int categoryId,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		List<BookInfo> listBook = null;
		String message = "";
		PaginationResult<BookInfo> result = null;
		if (categoryId == 0) {
			return new ModelAndView("welcome");
		}
		listBook = bookInfoDAO.getByCategoryId(categoryId);
		log.info("Number of record: " + listBook.size());
		result = new PaginationResult<>(listBook, page, MAX_RESULT, MAX_NAVIGATION_PAGE);
		ModelAndView mav = new ModelAndView("listBook");
		if (listBook.isEmpty()) {
			message = "Don't find any book in our store";

		}
		log.info("Message==============>" + message);
		mav.addObject("message", message);
		mav.addObject("paginationBooks", result);
		mav.addObject("categoryId", categoryId);
		log.info("categoryId = " + categoryId);

		return mav;
	}

	/**** shopping cart method ****/
	@GetMapping("/showMyCart")
	public ModelAndView showCart(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("showShoppingCart");
		ShoppingCart myCart = ShoppingCartService.getCart(request);

		Map<Integer, ShoppingCartIem> cartItems = myCart.getCartItems();
		cartItems.forEach((bookId, cartItem) -> {
			int realInStock = bookDAO.getBookById(bookId).getQtyInStock();
			if (cartItem.getBookInfo().getQtyInStock() != realInStock) {
				cartItem.getBookInfo().setQtyInStock(realInStock);
			}
		});
		myCart.setCartItems(cartItems);
		mav.addObject("myCart", myCart);

		return mav;
	}

	@GetMapping("/addCart")
	public String buyItem(HttpServletRequest request, @RequestParam("bookId") int bookId) {

		ShoppingCart myCart = ShoppingCartService.getCart(request);
		Map<Integer, ShoppingCartIem> cartItems = myCart.getCartItems();
		ShoppingCartIem item = null;
		if (!cartItems.isEmpty() && cartItems.containsKey(bookId)) {
			item = cartItems.get(bookId);
			if(bookInfoDAO.getByBookId(bookId).getQtyInStock()>=item.getQuantity()+1)
				item.setQuantity(item.getQuantity() + 1);

		} else {
			item = new ShoppingCartIem();
			item.setBookInfo(bookInfoDAO.getByBookId(bookId));
			item.setQuantity(1);
		}
		// add item to shopping cart
		cartItems.put(bookId, item);
		// add to session object
		myCart.setCartItems(cartItems);

		ShoppingCart newShopping = (ShoppingCart) request.getSession().getAttribute("shoppingCart");
		cartItems = newShopping.getCartItems();

		log.info("*************MY CART AFTER ADD TO CART IN SESSION**********");
		for (Map.Entry<Integer, ShoppingCartIem> bookItem: cartItems.entrySet()) {
			log.info("Cart Item key = " + bookItem.getKey() + " quantity " + bookItem.getValue().getQuantity());
		}

		return "redirect:/showMyCart";

	}

	@PostMapping("/updateCart")
	public String updateItem(HttpServletRequest request, @RequestParam("quantity") int quantity,
			@RequestParam("bookId") int bookId) {
		ShoppingCart myCart = ShoppingCartService.getCart(request);
		Map<Integer, ShoppingCartIem> cartItems = myCart.getCartItems();
		ShoppingCartIem item = null;

		if (!cartItems.isEmpty() && cartItems.containsKey(bookId)) {
			item = cartItems.get(bookId);

			// if quantity==0 then remove in shopping cart
			if (item.getQuantity() <= 0)
				cartItems.remove(bookId);
			else {
				// add item to shopping cart
				item.setQuantity(quantity);
				cartItems.put(bookId, item);
			}

			myCart.setCartItems(cartItems);
		}

		ShoppingCart newShopping = (ShoppingCart) request.getSession().getAttribute("shoppingCart");
		cartItems = newShopping.getCartItems();

		log.info("*************MY CART AFTER MINUS IN SESSION**********");
		for (Map.Entry<Integer, ShoppingCartIem> bookItem: cartItems.entrySet()) {
			log.info("Cart Item key = " + bookItem.getKey() + " quantity " + bookItem.getValue().getQuantity());
		}

		return "redirect:/showMyCart";
	}

	@GetMapping("/deleteCart")
	public String removeItem(HttpServletRequest request, @RequestParam("bookId") int bookId) {
		ShoppingCart myCart = ShoppingCartService.getCart(request);
		Map<Integer, ShoppingCartIem> cartItems = myCart.getCartItems();

		if (!cartItems.isEmpty() && cartItems.containsKey(bookId)) {
			cartItems.remove(bookId);

			myCart.setCartItems(cartItems);
		}

		return "redirect:/showMyCart";
	}

	@GetMapping("/cart/checkLogin")
	public String checkLoginForCheckOut(HttpServletRequest request, Model model) {

		log.info("URL get called: /cart/checkLogin");

		ShoppingCart myCart = ShoppingCartService.getCart(request);
		// if shopping cart don't have any item to buy
		if (myCart == null || myCart.getCartItems().isEmpty()) {
			log.info("Shopping Cart is empty.");

			model.addAttribute("errors", "You can't check out. Please add somes items to your cart for check out");
			return "redirect:/showMyCart";
		}

		String userName = "";

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// if user haven't been login yet
		if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
			log.info("User has not logged in. Please login");
			return "redirect:/login";

		} else {
			log.info("User has logged in.");
			User u = (User) authentication.getPrincipal();
			userName = u.getUsername();
			log.info("getPrincipal: " + userName);
		}

		log.info("Dao gets customer info.");

		log.info(String.format("username: %s", ((userName == null) ? "username is null" : userName) ));

		Customer customer = customerDAO.getCustomerByEmail(userName);

		// store customer id in session object
		myCart.setCustomer(customer);

		// if user already login and shopping cart have some item to check out
		// check customer card information

		log.info("User has logged in: " + userName);

		model.addAttribute("myCart", myCart);

		return "redirect:/cart/showShipping";

	}

	@GetMapping("/cart/showShipping")
	public ModelAndView showFormShippingAddress(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();
		Customer customer = ShoppingCartService.getCart(request).getCustomer();
		ShippingAddress shippingAddress = new ShippingAddress();
		shippingAddress.setAddress(customer.getAddress());
		shippingAddress.setCity(customer.getCity());
		shippingAddress.setState(customer.getState());
		shippingAddress.setZipcode(customer.getZipcode());
		
		
		mav.addObject("shippingAddress", shippingAddress);
		
		List<StaticMaster> list = staticDAO.getAllStates();
		Map<String, String> states = list.stream().collect(Collectors.toMap(StaticMaster::getCode, StaticMaster::getName));
		mav.addObject("states", states);
		
		mav.setViewName("showShippingAddress");
		log.info("Shipping address " + shippingAddress);
		return mav;

	}

	/**
	 * This method call after enter shipping address form
	 * 
	 * @param request
	 * @param shipping
	 *            ShippingAddress information that user enter in shipping address
	 *            form
	 * @param checkbox
	 *            it is null if user enter new address.It is not null if customer
	 *            choose the same address
	 * @param results
	 *            BindingResult has error when validator shipping address
	 *            information or not
	 * @param mav
	 *            Model that will go after call this URL
	 * @return
	 */
	@PostMapping("/billingAddress")
	public String showbillingAddressForm(HttpServletRequest request,
			@ModelAttribute("shippingAddress") @Validated ShippingAddress shipping,
			@RequestParam(value = "cbtheSame", required = false) String checkbox, BindingResult results, Model mav) {

		if (results.hasErrors()) {
			log.error("Error when validator shipping address when attempt call URL:/billingAddress");
			return "showShippingAddress";
		}
		ShoppingCart myCart = ShoppingCartService.getCart(request);
		Customer cust = myCart.getCustomer();
		log.info("Customer at Url: /billingAddress === " + cust);

		//1.1 if(checkbox is unchecked) => Customer want to shipping to new address
		if (checkbox == null) {
			// check new address must be different customer address
			if (shipping.getAddress().equals(cust.getAddress()) && shipping.getCity().equals(cust.getCity())
					&& shipping.getState().equals(cust.getState()) && shipping.getZipcode().equals(cust.getZipcode())) {
				mav.addAttribute("errors", "This address the same customer address");
				return "showShippingAddress";

			}
			// shipping id default=-1 for new address
			shipping.setId(-1);
			// 1.2 Customer choose the same address
		} else {
			// shipping id default=0 for the same address
			shipping.setId(0);
			shipping.setAddress(cust.getAddress());
			
		}

		// 2. store shipping address to session object

		myCart.setShippingAddress(shipping);

		// 3. List all customer's card for payment
		List<CustomerCard> listCard = customerCardDAO.getCardsByCustId(myCart.getCustomer().getId());
		Map<Integer, String> mapCards = new HashMap<>();

		// 3.1 If customer have card in database
		listCard.forEach(card -> {

			mapCards.put(card.getCardId(), card.getCardNumber());

			log.info("CARD FOR PAYMENT =" + card.getCardId() + " " + card.getCardNumber());
		});
		
		/*		 3.2 if customer don't have any card to check out. Enter information about visa card for payment
		*/
	if ( listCard.isEmpty()) {
			log.error("customer don't have any card to check out. Enter information about visa card");
			mav.addAttribute("customerCard", new CustomerCard());
			mav.addAttribute("errors",
					"Hi " + cust.getFirstName() + ", you don't have any card for payment. Please fill it to continue!");

			return "addCustomerCard";
		}
		// else customer have many card for payment
		else {

			// 4. go to payment method page
			mav.addAttribute("mapCards", listCard);
		}
		return "paymentMethod";
	}

	@PostMapping("/cart/shoppingConfirm")
	public ModelAndView showConfirmation(HttpServletRequest request, @RequestParam("cardId") int cardId,
			@RequestParam("isOldCard") boolean isOldCard) {
		ModelAndView mav = new ModelAndView("shoppingConfirmation");

		ShoppingCart myCart = ShoppingCartService.getCart(request);
		log.info("LOG in URL://cart/shoppingConfirm");
		log.info("isOldCard=" + isOldCard);
		if (isOldCard) {

			CustomerCard customerCard = customerCardDAO.getCartByCardId(cardId);
			log.info("customerCard= " + customerCard);
			myCart.setCustomerCard(customerCard);
		}
		mav.addObject("myCart", myCart);
		return mav;
	}

	@RequestMapping(value = "/cart/saveOrder", method = RequestMethod.POST)
	public String saveOrders(HttpServletRequest request, Model model) {
		log.info("LOG FOR URL /cart/saveOrder:");
		ShoppingCart myCart = ShoppingCartService.getCart(request);
		
		// save orders
		int orderId=0;
		try {
			orderId= orderManagerDAO.saveOrders(myCart);
		} catch (OutOfStockException | SQLException e) {
			model.addAttribute("errors", e.getMessage());
			log.error("Error at saveOrders "+e.getMessage());
			
			return "showShoppingCart";
		}
		// 2. remove shopping cart in your session
		ShoppingCartService.removeCart(request);
		
	//	model.addAttribute("custId", Utils.getCustIdFromSession(request));
		model.addAttribute("message", "Thank your for your order. Your order is successfull. Here is detail your order");
		
		return "success";

	}

	@GetMapping("/order/{orderId}")
	public String showOrderDetails(@PathVariable("orderId") int orderId,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "year", required = false) Integer year, @RequestParam("page") int page, Model model) {

		OrderInfo orderInfo = orderInfoDAO.getOrderInfoById(orderId);
		List<OrderDetailInfo> orderDetails = orderDetailInfoDAO.getAllOrderDetailInfoByOrderId(orderId);
		model.addAttribute("orderInfo", orderInfo);
		model.addAttribute("orderDetails", orderDetails);
		model.addAttribute("date", orderInfo.getCreateDate());
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		model.addAttribute("page", page);
		return "viewOrderDetail";

	}

	@GetMapping("/showChangePass")
	public String showChangePassForm(HttpServletRequest request, Model model) {

		log.info("****************URL /changePassword");
		if (Utils.getUserNameFromSession(request) == null)
			return "redirect:/login";
		FormChangePassword user = new FormChangePassword();
		user.setUserName(Utils.getUserNameFromSession(request));
		model.addAttribute("userChange", user);
		return "changePassword";

	}

	@PostMapping("/changePass")
	public String doChangePass(HttpServletRequest request,
			@ModelAttribute("userChange") @Validated FormChangePassword user, BindingResult results, Model model) {

		log.info("****************URL /changePassword");

		if (results.hasErrors()) {
			log.error("Error at doChangePass in validation step and binding data");
			return "changePassword";
		}
		//if update fail
		if (!userRoleDAO.updateUser(user.getUserName(), user.getPassword(), user.getNewPass())) {
			log.error("Incorrect Pass");
			results.rejectValue("password", "FormChangePass.password.incorrect");
			return "changePassword";
		}
		model.addAttribute("message", "Change password is successful.");
		return "success";

	}
	
	@GetMapping("/contactUsForm")
	public ModelAndView showFormContact() {
		ModelAndView mav =  new ModelAndView("contactUs");
		mav.addObject("contact", new ContactUs());
		return mav;
	}
	// This Method Is Used To Prepare The Email Message And Send It To The Client
	@PostMapping("/contactus/sendEmail")
	public ModelAndView sendEmailToClient(@ModelAttribute("contact") ContactUs contact) {

		String emailSubject = contact.getSubject();
		StringBuilder emailMessage = new StringBuilder();
		emailMessage.append("From " + contact.getEmail() +"\r\n")
					.append("Customer Name: " + contact.getFirstName() + contact.getLastName()+"\r\n")
					.append("Message:\r\n"+contact.getMessage());
		String emailToRecipient = "kieubookstore@gmail.com";
		

		ModelAndView mav = new ModelAndView();

		try {
			mailService.sendMail(emailToRecipient, emailSubject, emailMessage.toString());
		} catch (Exception e) {
			log.error(e.getMessage());
			mav.addObject("message",
					String.format("Fail to sent email to %s  with detail errors %s " , emailToRecipient ,  e.getMessage()));
			mav.setViewName("fail");
			return mav;


		}

		mav = new ModelAndView("success", "message", "Thank You for contact us! Your Email Has Been Sent! ");
		return mav;
	}

	@GetMapping("/forgot-password")
	public ModelAndView showForgotForm() {
		return new ModelAndView("forgotPassword");
	}

	@PostMapping("/sendemailforgotpass")
	public ModelAndView sendPassword(@RequestParam("email") String email) {

		ModelAndView mav = new ModelAndView();
		UserRole user = userRoleDAO.getByUserName(email);
		if (user != null) {

			String emailSubject = "Forgot password from kieu book store website.";

			// store new password to database
			String newPassword = userRoleDAO.resetPassword(email);

			String emailMessage = "Your username: " + user.getUserName() + "\r\n New password: " + newPassword
					+ "\r\n We're here to help.\r\n"
					+ "If you have questions, contact us online or visit a our book store. Thank for using our service. \r\n"
					+ "";
			String emailToRecipient = "kieu2812@gmail.com";
			try {
				mailService.sendMail(emailToRecipient, emailSubject, emailMessage);
			} catch (Exception e) {
				log.error("Error at sendPassword "+e.getMessage());
				mav.addObject("message", String.format("Fail to sent email to %s  with detail errors %s " , emailToRecipient ,  e.getMessage()));
				mav.setViewName("fail");
				return mav;


			}

		}else {
			log.error("Email " + email  );
		}
		
		mav.addObject("message", "Please check your email. New Password is sent to email " + email);
		mav.setViewName("success");
		return mav;
	}

}
