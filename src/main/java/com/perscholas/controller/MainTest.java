package com.perscholas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.perscholas.dao.AuthorDAO;
import com.perscholas.model.Author;



public class MainTest {

//	@Autowired
//	BookDAO bookDAO;
//	
////	@Autowired
////	ReviewDAO reviewDAO;
//	
//	@Autowired
//	OrderReportDAO orderDAO;

	@Autowired
	static AuthorDAO authorDAO;
	public static void main(String[] args) {
		
		
		List<Author> authors=  authorDAO.findByName("Michael Willia");
		 if(authors!=null && !authors.isEmpty()) {
			 authors.forEach(System.out::println);
		 }
		
	}
}
