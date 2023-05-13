package com.swifticket.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
@CrossOrigin("*")
public class StatsController {
	
	@GetMapping("/general")
	public ResponseEntity<?> getGeneralStats() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/event/{id}")
	public ResponseEntity<?> getEventStats(@PathVariable String id) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/event/{id}/attendance")
	public ResponseEntity<?> getEventAttendanceStats(@PathVariable String id) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
