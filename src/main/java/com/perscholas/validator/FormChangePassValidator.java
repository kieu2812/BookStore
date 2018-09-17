package com.perscholas.validator;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.perscholas.model.FormChangePassword;

@Component
// This Validator support FormChangePassValidator class.
public class FormChangePassValidator implements Validator  {
	 private static Logger log = Logger.getLogger(FormChangePassValidator.class);
	 private static final Pattern EMAIL_REGEX =
	            Pattern.compile("^[\\w\\d._-]+@[\\w\\d.-]+\\.[\\w\\d]{2,6}$");
	 
	 private static final Pattern PASSWORD_REGEX =
	            Pattern.compile("^[A-Za-z0-9]+.*");
	
	@Override
	public boolean supports(Class<?> className) {
		 // This Validator support CustomerForm class.		
		return FormChangePassword.class.equals(className);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormChangePassword form = (FormChangePassword) target;
		System.err.println("CHECK VALID USER IN FORM CHANGE PASSWORD");
		log.info("CHECK VALID USER IN FORM CHANGE PASSWORD");
	
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "FormChangePass.password.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "FormChangePass.userName.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPass", "FormChangePass.newPass.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPass", "FormChangePass.confirmPass.empty");
		
	
		if(form.getPassword().length()<6 || form.getPassword().length()>15) {
			errors.rejectValue("password", "FormChangePass.password.length");
			
		}
		if(form.getNewPass().length()<6 || form.getNewPass().length()>15) {
			errors.rejectValue("password", "FormChangePass.password.length");
			
		}
		if(form.getConfirmPass().length()<6 || form.getConfirmPass().length()>15) {
			errors.rejectValue("password", "FormChangePass.password.length");
			
		}

		if(!EMAIL_REGEX.matcher(form.getUserName()).matches())
			errors.rejectValue("userName", "FormChangePass.userName.pattern");
		
		if(!PASSWORD_REGEX.matcher(form.getPassword()).matches())
			errors.rejectValue("password", "FormChangePass.password.pattern");
		if(!PASSWORD_REGEX.matcher(form.getNewPass()).matches())
			errors.rejectValue("newPass", "FormChangePass.newPass.pattern");
		if(!PASSWORD_REGEX.matcher(form.getConfirmPass()).matches())
			errors.rejectValue("confirmPass", "FormChangePass.confirmPass.pattern");
		
		if(!form.getConfirmPass().equals(form.getNewPass()))
			errors.rejectValue("newPass", "FormChangePass.newPass.match");
		
		
	}

}
