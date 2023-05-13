package com.swifticket.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {
	
	@GetMapping("")
	public ResponseEntity<?> getUsers() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getUser(@PathVariable String id) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<?> createUser() {
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("")
	public ResponseEntity<?> updateUser() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PatchMapping("/change-password")
	public ResponseEntity<?> changePassword() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PatchMapping("/toggle-status")
	public ResponseEntity<?> toggleStatus() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@PostMapping("/role")
	public ResponseEntity<?> assignRole() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/role")
	public ResponseEntity<?> removeRole() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@PostMapping("/assign-to-event")
	public ResponseEntity<?> assignToEvent() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/remove-from-event")
	public ResponseEntity<?> removeFromEvent() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
