package com.swifticket.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@CrossOrigin("*")
public class RoleController {
	
	@GetMapping("")
	public ResponseEntity<?> getRoles() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
