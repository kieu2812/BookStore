package com.perscholas.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.perscholas.model.CustomerCard;
import com.perscholas.model.ShoppingCart;
import com.perscholas.services.ShoppingCartService;
import com.perscholas.validator.CustomerCardValidator;

@Controller

public class CustomerCardController {
	
	static Logger log = Logger.getLogger(CustomerCardController.class);
	

	@Autowired
	CustomerCardValidator cardValidator;
	@GetMapping("/card/showCard")
	public ModelAndView showCustomerCardForm(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("addCustomerCard");
		CustomerCard card = new CustomerCard();
		
		mav.addObject("customerCard", card);
		
		return mav;
		
	}
	@PostMapping("/card/addCardProcess")
	public String showCustomerCardForm(HttpServletRequest request, 
			 @ModelAttribute("customerCard") CustomerCard customerCard , BindingResult bindingResult, Model model) {
		cardValidator.validate(customerCard, bindingResult);
		if (bindingResult.hasErrors()) {
	        
			log.error("SOME ERROR WHEN BINDING DATA FROM FORM ADD NEW CARD FOR PAYMENT");
			model.addAttribute("errors","SOME ERROR WHEN BINDING DATA FROM FORM ADD NEW CARD FOR PAYMENT");
			return "addCustomerCard";
	    }
	
		//set customerid and expireDate to customerCard to insert new card to database
		LocalDate expireDate = YearMonth
				.of(customerCard.getExpireYear(), customerCard.getExpireMonth())
				.atEndOfMonth();
		customerCard.setCustomerId(ShoppingCartService.getCart(request).getCustomer().getId());
		customerCard.setExpireDate(Date.valueOf(expireDate));
		
	
		// store cardIDGenerated to session;
		ShoppingCart myCart =  ShoppingCartService.getCart(request);
		myCart.setCustomerCard(customerCard);
		
		model.addAttribute("myCart",myCart);		
		return "shoppingConfirmation";
		
	}
	
}

