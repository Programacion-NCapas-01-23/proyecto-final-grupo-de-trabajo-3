package com.swifticket.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system")
@CrossOrigin("*")
public class SystemController {
	
	@PostMapping("/suspend-service")
	public ResponseEntity<?> suspendService() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
