package com.quest.etna.controller;


import com.quest.etna.model.*;
import com.quest.etna.repositories.UserRepository;
import com.quest.etna.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@CrossOrigin(origins = "*")
@RestController
public class BookController {
	@Autowired
    private final UserRepository userRepository;
   
	@Autowired
	private final BookService bookService;

    public BookController(UserRepository userRepository, BookService bookService) {
		this.userRepository = userRepository;
		this.bookService = bookService;
	}
    
  	@PostMapping(value="/book")
  	public ResponseEntity<?> addbook(@RequestBody Book book) {
    try {
		JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
	            .getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Optional<User> user = userRepository.findByUsername(userName);
		 if (user.get().getRole() == UserRole.ROLE_USER ) 
			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED) 
				 .body(new Erreur("Utilisateur non habilité"));
		 
		
	    return this.bookService.createBook(book);
	    }
    	catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Erreur("Mauvaise requête"));
        }
  	}
  	/*
  	@CrossOrigin(origins = "*")
 	@PutMapping(value="/book/{id}")
  	public ResponseEntity<?> updatebook(@RequestBody Book book, @PathVariable int id ) {
    try {
		JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
	            .getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Optional<User> user = userRepository.findByUsername(userName);
		 if (user.get().getRole() == UserRole.ROLE_USER ) 
			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED) 
				 .body(new Erreur("Utilisateur non habilité"));
		 
		return this.bookService.update(id, book);
	    }
    	catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Erreur("Mauvaise requête"));
        }
  	}
 	*/
	@CrossOrigin(origins = "*")
	@PutMapping(value = "/book/{id}")
	public ResponseEntity<?> partialUpdateBook(@RequestBody Map<String, Object> updates, @PathVariable int id) {
		try {
			JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			String userName = userDetails.getUsername();
			Optional<User> user = userRepository.findByUsername(userName);
			if (user.get().getRole() == UserRole.ROLE_USER)
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(new Erreur("Utilisateur non habilité"));

			return bookService.partialUpdate(id, updates);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new Erreur("Mauvaise requête"));
		}
	}


	@GetMapping("/books")
	public ResponseEntity<?> getList(@RequestParam(defaultValue="0") Integer page , @RequestParam(defaultValue="10") Integer limit){
		return ResponseEntity
                .status(HttpStatus.OK)
                .body( bookService.getList(page, limit));
	}
  	
  	
 
  	
	@DeleteMapping("/book/{id}")
    ResponseEntity<?> removeBooksById(@PathVariable int id){
		try {
			JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
		            .getAuthentication().getPrincipal();
			String userName = userDetails.getUsername();
			Optional<User> user = userRepository.findByUsername(userName);
			 if (user.get().getRole() == UserRole.ROLE_USER ) 
				 return ResponseEntity.status(HttpStatus.UNAUTHORIZED) 
					 .body(new Erreur("Utilisateur non habilité"));
			 return bookService.delete(id);
			}
			catch (Exception ex) {
	        	ex.printStackTrace();
	        	return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(new Erreur("Mauvaise requête"));
	    }
  	}
	
	
	@GetMapping("/book/{id}")
    ResponseEntity<?> getBooksById(@PathVariable int id){
		return bookService.checkBookById(id);
  	}
  	
  	
 }
