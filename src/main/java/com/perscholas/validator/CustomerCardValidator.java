package com.perscholas.validator;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.perscholas.dao.CustomerCardDAO;
import com.perscholas.model.CustomerCard;

@Component
public class CustomerCardValidator implements Validator {
	
	@Autowired
	CustomerCardDAO cardDAO;
	 private static Logger log = Logger.getLogger(CustomerValidator.class);
	 private static final Pattern CARD_NUMBER =
	            Pattern.compile("^[\\d]{13,20}");
	 private static final Pattern HOLDER_NAME =
	            Pattern.compile("^[A-Z][A-Za-z\\s]+[a-z]*$");
	@Override
	public boolean supports(Class<?> className) {
		
		return CustomerCard.class.equals(className);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		CustomerCard card= (CustomerCard) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cardNumber", "cardForm.cardNumber.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "holderName", "cardForm.holderName.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "expireMonth", "cardForm.expireMonth.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "expireYear", "cardForm.expireYear.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "securityCode", "cardForm.securityCode.empty");
		if(!CARD_NUMBER.matcher(card.getCardNumber()).matches())
			errors.rejectValue("cardNumber", "cardForm.cardNumber.pattern");
		if(!HOLDER_NAME.matcher(card.getHolderName()).matches())
			errors.rejectValue("holderName", "cardForm.holderName.pattern");

		log.info(card.toString());
		// if customer enter new cards for payment, insert customer card to database
				// ( check customer card is the same with old card or not when enter card
				// payment before confirmation form)
		String cardNumber=card.getCardNumber();
		
		
		//get the last day of the month and year;
		LocalDate expireDate = YearMonth
				.of(card.getExpireYear(), card.getExpireMonth())
				.atEndOfMonth();
		// if expireDate<now => invalid 
		if(expireDate.isBefore(LocalDate.now())) {
			errors.rejectValue("expireMonth", "cardForm.expireMonth.pattern");
			errors.rejectValue("expireYear", "cardForm.expireYear.pattern");
			
		}
		//else check card exists in database?
		//one card call duplicate if the same card number and expireDate and securityCode
		CustomerCard cardInDatabase = cardDAO.findCard(cardNumber, card.getSecurityCode(), card.getExpireMonth(), card.getExpireYear());
		
		
		//CARD EXISTS IN THE DATABASE 
		if(cardInDatabase!=null) {
			errors.rejectValue("cardNumber", "cardForm.duplicate");
		}
		
	
	}

}
