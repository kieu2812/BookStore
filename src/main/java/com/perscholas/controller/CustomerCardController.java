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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.perscholas.dao.CustomerCardDAO;
import com.perscholas.model.CustomerCard;
import com.perscholas.model.ShoppingCart;
import com.perscholas.services.ShoppingCartService;
import com.perscholas.validator.CustomerCardValidator;

@Controller

public class CustomerCardController {
	
	static Logger log = Logger.getLogger(CustomerCardController.class);
	
	@Autowired
	CustomerCardDAO cardDAO;
	
	@Autowired
	CustomerCardValidator cardValidator;
	@GetMapping("/card/showCard")
	public ModelAndView showCustomerCardForm(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("addCustomerCard");
		CustomerCard card = new CustomerCard();
		
		mav.addObject("custId", request.getSession().getAttribute("custId"));
		mav.addObject("customerCard", card);
		
		return mav;
		
	}
	@PostMapping("/card/addCardProcess")
	public String showCustomerCardForm(HttpServletRequest request, 
			 @Validated @ModelAttribute("customerCard")CustomerCard customerCard , BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
	        
			log.info("SOME ERROR WHEN BINDING DATA FROM FORM ADD NEW CARD FOR PAYMENT");
			model.addAttribute("errors","SOME ERROR WHEN BINDING DATA FROM FORM ADD NEW CARD FOR PAYMENT");
			return "addCustomerCard";
	    }
	
		//set customerid and expireDate to customerCard to insert new card to database
		LocalDate expireDate = YearMonth
				.of(customerCard.getExpireYear(), customerCard.getExpireMonth())
				.atEndOfMonth();
		customerCard.setCustomerId(ShoppingCartService.getCart(request).getCustomer().getId());
		customerCard.setExpireDate(Date.valueOf(expireDate));
		
		int cardIDGenerated = cardDAO.insertCard(customerCard);
		
		log.info("Customer card id = " + cardIDGenerated );
		
		// store cardIDGenerated to session;
		ShoppingCart myCart =  ShoppingCartService.getCart(request);
		
		customerCard.setCardId(cardIDGenerated);
		model.addAttribute("cardId", cardIDGenerated);
		model.addAttribute("isOldCard","false");
		myCart.setCustomerCard(customerCard);
		model.addAttribute("myCart",myCart);		
		return "shoppingConfirmation";
		
	}
	
}

