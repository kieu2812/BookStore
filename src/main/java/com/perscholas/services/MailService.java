package com.perscholas.services;

import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	static Logger log = Logger.getLogger(MailService.class);
	String emailFromRecipient = "kieubookstore@gmail.com";
	
	 private MailSender mailSender;  
	 
//	 public MailSender getMailSender() {
//		 return new JavaMailSenderImpl();
//	 }
	 public void setMailSender(MailSender mailSender) {  
	        this.mailSender = mailSender;  
	 }  	
	 
	 public void sendMail( String emailToRecipient, String emailSubject, String emailMessage) throws Exception, MailException{
		
		 SimpleMailMessage message = new SimpleMailMessage();
		 	message.setFrom(emailFromRecipient);
	        message.setTo(emailToRecipient);
	        message.setSubject(emailSubject);
	        message.setText(emailMessage);
	        mailSender.send(message);
		
		
		
		// Logging The Email Form Parameters For Debugging Purpose
		log.info("\nReceipient?= " + emailToRecipient + ", Subject?= " + emailSubject + ", Message?= " + emailMessage + "\n");

	
		log.info("\nMessage Send Successfully.... Hurrey!\n");

	}
	
	public void cleanUp() throws Exception {
		mailSender = null;
	}

}
