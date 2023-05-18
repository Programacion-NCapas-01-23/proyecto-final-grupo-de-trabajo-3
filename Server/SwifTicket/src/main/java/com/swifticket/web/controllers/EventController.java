package com.swifticket.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swifticket.web.models.dtos.event.SaveEventDTO;
import com.swifticket.web.models.entities.Category;
import com.swifticket.web.models.entities.Organizer;
import com.swifticket.web.models.entities.Place;
import com.swifticket.web.services.CategoryServices;
import com.swifticket.web.services.EventServices;
import com.swifticket.web.services.OrganizerServices;
import com.swifticket.web.services.PlaceServices;


@RestController
@RequestMapping("/events")
@CrossOrigin("*")
public class EventController {
	
	@Autowired
	private EventServices eventServices;
	@Autowired
	private CategoryServices categoryServices;
	@Autowired
	private PlaceServices placeService;
	@Autowired
	private OrganizerServices organizerService;
	
	@GetMapping("")
	public ResponseEntity<?> getEvents() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getEvent(@PathVariable String id) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<?> createEvent(@ModelAttribute SaveEventDTO data) {
		Category category = categoryServices.findById(data.getCategoryId());
		if (category == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		Place place = placeService.findById(data.getPlaceId());
		if (place == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		Organizer organizer = organizerService.findById(data.getOrganizerId());
		if (organizer == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		try {
			eventServices.save(data, category, organizer, place);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
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
