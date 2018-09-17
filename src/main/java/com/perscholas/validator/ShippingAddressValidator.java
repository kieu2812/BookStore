package com.perscholas.validator;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.perscholas.dao.ShippingAddressDAO;
import com.perscholas.model.ShippingAddress;


@Component
public class ShippingAddressValidator implements Validator  {
	// private static Logger log = Logger.getLogger(ShippingAddressValidator.class);
	
	 private static final Pattern ZIP_CODE_REGEX =
	            Pattern.compile("[0-9]{5,7}");
	 private static final Pattern ADDRESS_REGEX =
	            Pattern.compile("^\\d+[A-Za-z\\s]*$");
	 private static final Pattern WORD_REGEX =
	            Pattern.compile("^[A-Z][A-Za-z\\s]+[a-z]*$");

	@Autowired 
	ShippingAddressDAO shippingDAO;
	
	@Override
	public boolean supports(Class<?> className) {
		 // This Validator support CustomerForm class.		
		return ShippingAddress.class.equals(className);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ShippingAddress shipping = (ShippingAddress) target;
			
	
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "shipping.address.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "shipping.city.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "state", "shipping.state.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "zipcode", "shipping.zipcode.empty");
		
	
		if(!ZIP_CODE_REGEX.matcher(shipping.getZipcode()).matches())
			errors.rejectValue("zipcode", "shipping.zipcode.pattern");

		if(!ADDRESS_REGEX.matcher(shipping.getAddress()).matches())
			errors.rejectValue("address", "shipping.address.pattern");
		
		if(!WORD_REGEX.matcher(shipping.getCity()).matches())
			errors.rejectValue("city", "shipping.city.pattern");

		
	}
}
