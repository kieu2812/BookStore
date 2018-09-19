package com.perscholas.controller;


import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
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
import org.springframework.web.servlet.ModelAndView;

import com.perscholas.dao.CustomerDAO;
import com.perscholas.dao.OrderDetailInfoDAO;
import com.perscholas.dao.OrderInfoDAO;
import com.perscholas.dao.OrderReportDAO;
import com.perscholas.dao.ReviewDAO;
import com.perscholas.dao.StaticMasterDAO;
import com.perscholas.dao.UserRoleDAO;
import com.perscholas.model.Customer;
import com.perscholas.model.CustomerForm;
import com.perscholas.model.OrderDetailInfo;
import com.perscholas.model.OrderInfo;
import com.perscholas.model.PaginationResult;
import com.perscholas.model.Review;
import com.perscholas.model.ShippingAddress;
import com.perscholas.model.StaticMaster;
import com.perscholas.model.UserRole;
import com.perscholas.services.Utils;
import com.perscholas.validator.CustomerValidator;

@Controller
public class CustomerController {
	final static int MAX_RESULT = 5;
	final static int MAX_NAVIGATION_PAGE = 5;
	static int ROLE_ADMIN=1;
	static int ROLE_USER=2;
	static Logger log = Logger.getLogger(CustomerController.class);
	
	@Autowired
	CustomerDAO custDAO ;
	
	@Autowired
	UserRoleDAO userSer;
	
	@Autowired
	CustomerValidator customerValidator;
	
	@Autowired
	StaticMasterDAO staticDAO;
	
	@Autowired
	OrderReportDAO orderReportDAO;
	
	@Autowired
	OrderInfoDAO ordeInfoDAO;
	
	@Autowired
	OrderDetailInfoDAO orderDetailInfoDAO;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Autowired
	ReviewDAO reviewDAO;
	

	
//	@InitBinder
//	protected void initBinder(WebDataBinder binder) {
//		binder.addValidators(customerValidator);
//   }

	//@GetMapping("/customer/showSignUp")
	 @RequestMapping(value = "/customer/showSignUp", method = RequestMethod.GET)
	public ModelAndView showRegisterForm() {
		ModelAndView mav  = new ModelAndView("register");
		CustomerForm customerForm = new CustomerForm();
		mav.addObject("customerForm", customerForm);
		List<StaticMaster> list = staticDAO.getAllStates();
		Map<String, String> states = list.stream().collect(Collectors.toMap(StaticMaster::getCode, StaticMaster::getName));
		mav.addObject("states", states);
		
		return mav;
	}
	 @RequestMapping(value = "/customer/registerProcess", method = RequestMethod.POST)
	public String registerCustomer(@ModelAttribute("customerForm") CustomerForm custForm
			, BindingResult results, Model model
			) {
		log.info("URL /customer/registerProcess is call" );
		
		customerValidator.validate(custForm, results);
		
		if(results.hasErrors()) {			
			log.info("Error in binding data");
			List<StaticMaster> list = staticDAO.getAllStates();
			Map<String, String> states = list.stream().collect(Collectors.toMap(StaticMaster::getCode, StaticMaster::getName));
			model.addAttribute("states", states);
			
			 return "register";
		}
		
		Customer cust = custForm.getCustomer();
	
		log.info("Valid data, continue insert to customer table in the database");
		log.info("Customer information: " + cust);
		int custId=  custDAO.insertCustomer(cust);
		log.info("insert customer successful. custID=" + custId+ ". Go to insert user to user_role");
		String message=" Register successfull. Hi, "+cust.getFirstName()+". Your username: " + cust.getEmail(); 
		
		
		// if insert user successful in customer table
		if (custId>0) {
			//insert user to user_role table
			// encrypt password
			log.info("insert customer successful. custID=" + custId+ ". Go to insert user to user_role");
			
			String password = encoder.encode(custForm.getPassword());
			
			UserRole user= new UserRole();
			user.setUserName(cust.getEmail());
			user.setPassword(password);
			user.setCustId(custId);
			user.setRole(ROLE_USER);
			log.info("User before insert to table user_role: "+ user);
			userSer.addUser(user);
			model.addAttribute("message", message);
			log.info("insert user to user_role is successful");


			return "success";
		}else {
			model.addAttribute("errors", "Insert fail");
			return "customer/showSignUp";
		}
		
	  }
	 @GetMapping("/customer/showAddAddress")
	 public ModelAndView showFormAddress() {
		 ModelAndView mav= new ModelAndView();
		 mav.addObject("shippingAddress", new ShippingAddress());
		 return mav;
		 
	 }
	 @GetMapping("/customer/showMyOrder")
	 public String showMyOrder(HttpServletRequest request,
			 @RequestParam(value="page",defaultValue="1") int page,
			 @RequestParam(value="custId", required=false) Integer custId, Model model) {
		
			log.info("****************URL /customer/showMyOrder");
			PaginationResult<OrderInfo> result=null;
			String message=null;
			
			// check user login or not.
			Object userName = request.getSession().getAttribute("userName");
			if(userName==null)
				return "redirect:/login";
			
			int customerId =0;
			if(custId==null) {
				Customer cust= custDAO.getCustomerByEmail((String) userName);
				customerId =  cust.getId();
			}else {
				customerId= custId;
			}
			List<OrderInfo> orderInfos = ordeInfoDAO.getAllOrderInfoByCustId(customerId);
			

			if (orderInfos != null && orderInfos.isEmpty()) {
				message = "Don't find any order in the our store";
				model.addAttribute("message", message);
				return "fail";
			}
			log.info("Message==============>" + message);
			result= new PaginationResult<>(orderInfos, page, MAX_RESULT, MAX_NAVIGATION_PAGE);
			model.addAttribute("paginationOrders", result);
			model.addAttribute("custId",customerId);
			return "orderList";
	 }
	 @GetMapping("/customer/{custId}/order/{orderId}")
	 public String viewOrderDetail(	HttpServletRequest request,
			 @PathVariable("orderId") int orderId,
			 @PathVariable(value="custId") int custId,
			 @RequestParam(value="page",defaultValue="1") int page, Model model){
		 
		 	model.addAttribute("custId",  custId );
			log.info("****************URL /customer/{custId}/order/{orderId}");
			OrderInfo orderInfo = ordeInfoDAO.getOrderInfoById(orderId);
			if(orderInfo==null) {
				
				model.addAttribute("errors", "This order can't find in the system");
				return "redirect:/customer/showMyOrder";
			}
			 List<OrderDetailInfo> orderDetails = orderDetailInfoDAO.getAllOrderDetailInfoByOrderId(orderId);
			model.addAttribute("orderInfo", orderInfo);
			model.addAttribute("orderDetails", orderDetails);
			model.addAttribute("page", page);
			
			return "orderDetail";

	 }
	 @PostMapping("/customer/writeReview")
	 public String writeReview(HttpServletRequest request, 
			 @RequestParam("rating")int rating, 
			 @RequestParam("comments") String comments,
			 @RequestParam("bookid") int bookid, Model model) {
		 Review review= new Review();
		 review.setComments(comments);
		 review.setBookid(bookid);
		 review.setCreateDate(Date.valueOf(LocalDate.now()));
		 review.setPosterid((Integer)Utils.getCustIdFromSession(request));
		 review.setRating(rating);
		 log.info(review);
		 reviewDAO.insertReview(review);
		 
		 model.addAttribute("bookId", bookid);
		 
		 return "redirect:/book/getBook";
	 }
	 
	 @PostMapping("/customer/updateReview")
	 public String updateReview(HttpServletRequest request, 
			 @RequestParam("rating")int rating, 
			 @RequestParam("comments") String comments,
			 @RequestParam("bookId") int bookId,
			 @RequestParam("id") int id, Model model) {
		 Review review= new Review();
		 review.setComments(comments);
		 review.setBookid(bookId);
		 review.setId(id);
		 review.setCreateDate(Date.valueOf(LocalDate.now()));
		 review.setPosterid((Integer)Utils.getCustIdFromSession(request));
		 log.info(review);
		 reviewDAO.updateReview(review);
		 model.addAttribute("bookId", bookId);
//		 
		 return "redirect:/book/getBook";
	 }
	 @GetMapping("/customer/deleteReview/{bookId}/{reviewId}")
	 public String deleteReview(@PathVariable("bookId") int bookId,
			 @PathVariable("reviewId") int reviewId , Model model) {
		 String message;
		 if(reviewDAO.deleteReview(reviewId)) {
			 message="ReviewID= "+reviewId+"This review doesn't exist";
			 log.info(message);
			 model.addAttribute("error", message);
			 model.addAttribute("bookId", bookId);
		 }
//		 
		 return "redirect:/book/getBook";
	 }
}
