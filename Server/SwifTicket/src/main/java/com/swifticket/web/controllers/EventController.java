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
@RequestMapping("/events")
@CrossOrigin("*")
public class EventController {
	
	@GetMapping("")
	public ResponseEntity<?> getEvents() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getEvent(@PathVariable String id) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<?> createEvent() {
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateEvent(@PathVariable String id) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PatchMapping("/change-status")
	public ResponseEntity<?> patchEvent() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@PostMapping("/sponsors")
	public ResponseEntity<?> assignSponsor() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/sponsors")
	public ResponseEntity<?> removeSponsor() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@GetMapping("/{id}/tiers")
	public ResponseEntity<?> getEventTiers(@PathVariable String id) {
		return new ResponseEntity<>("event tiers", HttpStatus.OK);
	}
	
	@PostMapping("/tiers")
	public ResponseEntity<?> createEventTier() {
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/tiers/{tierId}")
	public ResponseEntity<?> updateEventTier(@PathVariable String tierId) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/tiers/{tierId}")
	public ResponseEntity<?> deleteEventTier(@PathVariable String tierId) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
