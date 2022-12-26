package com.masai.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/masai")
public class WelcomeController {

	//This should not be protected
	@GetMapping("/welcome")
	public ResponseEntity<String> welcome(){
		
	 return new ResponseEntity<String>("Welcome to Masai App without security",HttpStatus.ACCEPTED);
	}
	
	//This should  be protected
	@GetMapping("/welcomeP")
	public ResponseEntity<String> welcomeP(){
		
			return new ResponseEntity<String>("Welcome to Masai App with Security",HttpStatus.ACCEPTED);
	}
}
