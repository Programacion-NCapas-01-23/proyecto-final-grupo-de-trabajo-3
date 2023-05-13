package com.swifticket.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organizers")
@CrossOrigin("*")
public class OrganizerController {

	@GetMapping("")
	public ResponseEntity<?> getOrganizers() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<?> createOrganizer() {
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateOrganizer(@PathVariable String id) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteOrganizer(@PathVariable String id) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
