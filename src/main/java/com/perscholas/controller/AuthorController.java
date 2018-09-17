package com.perscholas.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.perscholas.dao.AuthorDAO;
import com.perscholas.model.Author;

@RestController
@RequestMapping("/author")
public class AuthorController {
	
	static Logger log = Logger.getLogger(AuthorController.class);
	@Autowired
	AuthorDAO authorDAO;
	
	
	@GetMapping("/list")
	public List<Author> getAllAuthor() {

		List<Author> authors = authorDAO.getAllAuthors();
		return authors;
		
	}
	
	@GetMapping("/find/")
	public List<Author> findByName(@RequestParam("name")String name) {

		List<Author> authors = authorDAO.findByName(name);
		return authors;
	}
//	@GetMapping("/get/{id}")
//	public ResponseEntity<Author> getByName(@PathVariable("id")int id) {
//
//		Author author = authorDAO.getAuthorById(id);
//		log.info("Author " + author);
//		if(author==null) {
//			return new ResponseEntity<Author>(HttpStatus.NOT_FOUND);
//		}
//		return new ResponseEntity<Author>(HttpStatus.OK);
//	}	
//	@PostMapping("/create/")
//	public ResponseEntity<Void> insert(@RequestBody String name, UriComponentsBuilder uriComponentsBuilder) {
//
//		Author author = authorDAO.getAuthorByName(name);
//		log.info("Author " + author);
//
//		if(author!=null) {
//			
//			return new ResponseEntity<>(HttpStatus.CONFLICT);
//		}
//		else {
//			int id = authorDAO.addAuthor(name);
//			log.info("New Author ID " + id);
//
//			HttpHeaders headers = new HttpHeaders();
//			headers.setLocation(uriComponentsBuilder.path("/author/{id}").buildAndExpand(id).toUri());
//			return new ResponseEntity<Void>(headers, HttpStatus.CREATED) ;
//		}
//	}	
//	@PutMapping("/update/")
//	public ResponseEntity<Void> update(@RequestBody Author authorUpdate, UriComponentsBuilder uriComponentsBuilder) {
//
//		Author author =  authorDAO.getAuthorById(authorUpdate.getId());
//		
//		if(author!=null) {
//			
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//		else {
//			boolean success= authorDAO.updateAuthor(authorUpdate.getId(),authorUpdate.getName());
//			
//			HttpHeaders headers = new HttpHeaders();
//			headers.setLocation(uriComponentsBuilder.path("/author/{id}").buildAndExpand(authorDAO.getAuthorById(authorUpdate.getId())).toUri());
//			return new ResponseEntity<Void>(headers, HttpStatus.OK) ;
//		}
//	}	
//	
//	@DeleteMapping("/delete/{id}")
//	public ResponseEntity<Void> delete(@PathVariable("id") int id, UriComponentsBuilder uriComponentsBuilder) {
//
//		Author author =  authorDAO.getAuthorById(id);
//		
//		if(author!=null) {
//			
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//		else {
//			authorDAO.removeAuthor(id);
//			
//			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT) ;
//		}
//	}	
	
	@GetMapping("/get/{id}")
	public Author getByName(@PathVariable("id")int id) {

		Author author = authorDAO.getAuthorById(id);
		log.info("Author " + author);
		return author;
	}	
	@PostMapping("/create/")
	public Author insert(@RequestBody String name, UriComponentsBuilder uriComponentsBuilder) {

		Author author = authorDAO.getAuthorByName(name);
		log.info("Author " + author);

			int id = authorDAO.addAuthor(name);
			log.info("New Author ID " + id);

		
		return authorDAO.getAuthorById(id);
	}	
	@PutMapping("/update/")
	public Author update(@RequestBody Author authorUpdate) {

		Author author =  authorDAO.getAuthorById(authorUpdate.getId());
		
		if(author!=null) {
			
			authorDAO.updateAuthor(authorUpdate.getId(),authorUpdate.getName());
			return authorDAO.getAuthorById(authorUpdate.getId());
			
		}
		return null;
	}	
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable("id") int id, UriComponentsBuilder uriComponentsBuilder) {

		Author author =  authorDAO.getAuthorById(id);
		
		if(author!=null) {
			
		}
		else {
			authorDAO.removeAuthor(id);
			
			
		}
	}	
	
}
