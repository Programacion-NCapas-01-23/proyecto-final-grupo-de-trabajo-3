package com.swifticket.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
@CrossOrigin("*")
public class TicketController {

	@GetMapping("/{id}")
	public ResponseEntity<?> getTicket(@PathVariable String id) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getTicketsByUser(@PathVariable String id) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<?> createTicket() {
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PostMapping("/generate-code")
	public ResponseEntity<?> generateValidateTicketCode() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PatchMapping("/validate-ticket")
	public ResponseEntity<?> validateTicket() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/transfer")
	public ResponseEntity<?> startTransferTicket() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/transfer/{id}")
	public ResponseEntity<?> acceptTransferTicket() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/transfer/validate-transfer/{code}")
	public ResponseEntity<?> finishTransferTicket(@PathVariable String code) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
