package com.swifticket.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
	
	@GetMapping("/validate-token")
	public ResponseEntity<?> validateToken() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/validate-account/{code}")
	public ResponseEntity<?> validateAccount(@PathVariable String code) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> signIn() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
