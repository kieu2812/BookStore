package com.perscholas.validator;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.perscholas.dao.CustomerDAO;
import com.perscholas.model.Customer;
import com.perscholas.model.CustomerForm;

@Component
// This Validator support CustomerForm class.
public class CustomerValidator implements Validator  {
	 private static Logger log = Logger.getLogger(CustomerValidator.class);
	 private static final Pattern EMAIL_REGEX =
	            Pattern.compile("^[\\w\\d._-]+@[\\w\\d.-]+\\.[\\w\\d]{2,6}$");
	 
	 private static final Pattern ZIP_CODE_REGEX =
	            Pattern.compile("[0-9]{5,7}");
	 private static final Pattern ADDRESS_REGEX =
	            Pattern.compile("^\\d+[A-Za-z\\s]*$");
	 private static final Pattern WORD_REGEX =
	            Pattern.compile("^[A-Z][A-Za-z\\s]+[a-z]*$");

	@Autowired 
	CustomerDAO custDAO;
	
	@Override
	public boolean supports(Class<?> className) {
		 // This Validator support CustomerForm class.		
		return CustomerForm.class.equals(className);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CustomerForm custForm = (CustomerForm) target;
		Customer cust = custForm.getCustomer();
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "custForm.password.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customer.firstName", "custForm.firstName.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customer.lastName", "custForm.lastName.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customer.address", "custForm.address.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customer.city", "custForm.city.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customer.state", "custForm.state.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customer.email", "custForm.email.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customer.zipcode", "custForm.zipcode.empty");
		
		if(custForm.getPassword().length()<6 && custForm.getPassword().length()>15) {
			errors.rejectValue("password", "custForm.password.length");
			
		}
		if(!EMAIL_REGEX.matcher(cust.getEmail()).matches())
			errors.rejectValue("customer.email", "custForm.email.pattern");
		if(!ZIP_CODE_REGEX.matcher(cust.getZipcode()).matches())
			errors.rejectValue("customer.zipcode", "custForm.zipcode.pattern");

		if(!ADDRESS_REGEX.matcher(cust.getAddress()).matches())
			errors.rejectValue("customer.address", "custForm.address.pattern");
		
		if(!WORD_REGEX.matcher(cust.getFirstName()).matches())
			errors.rejectValue("customer.firstName", "custForm.firstName.pattern");
		
		if(!WORD_REGEX.matcher(cust.getLastName()).matches())
			errors.rejectValue("customer.lastName", "custForm.lastName.pattern");
		
		if(!WORD_REGEX.matcher(cust.getCity()).matches())
			errors.rejectValue("customer.city", "custForm.city.pattern");
				
		log.info("input email: " + cust.getEmail());
		if(custDAO.getCustomerByEmail(cust.getEmail())!=null) {
			errors.rejectValue("customer.email", "custForm.email.duplicate");
			log.info("Duplicate email in CustomerValidator.class");
		}
		
		
	}

}
