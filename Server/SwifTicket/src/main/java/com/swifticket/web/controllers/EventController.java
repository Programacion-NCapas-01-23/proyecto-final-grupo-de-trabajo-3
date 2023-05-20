package com.swifticket.web.controllers;

import java.util.List;

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

import com.swifticket.web.models.dtos.event.ChangeEventStatusDTO;
import com.swifticket.web.models.dtos.event.SaveEventDTO;
import com.swifticket.web.models.entities.Category;
import com.swifticket.web.models.entities.Event;
import com.swifticket.web.models.entities.EventState;
import com.swifticket.web.models.entities.Organizer;
import com.swifticket.web.models.entities.Place;
import com.swifticket.web.models.entities.Tier;
import com.swifticket.web.services.CategoryServices;
import com.swifticket.web.services.EventServices;
import com.swifticket.web.services.EventStateServices;
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
	@Autowired
	private EventStateServices eventStateService;

	@GetMapping("")
	public ResponseEntity<?> getEvents() {
		List<Event> events = eventServices.findAll();
		return new ResponseEntity<>(events, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getEvent(@PathVariable String id) {
		Event event = eventServices.findOneById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/category/{categoryId}")
	public ResponseEntity<?> getEventByCategory(@PathVariable int categoryId) {
		Category category = categoryServices.findById(categoryId);

		if (category == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		List<Event> events = category.getEvents();
		return new ResponseEntity<>(events, HttpStatus.OK);
	}

	@GetMapping("/state/{stateId}")
	public ResponseEntity<?> getEventByState(@PathVariable int stateId) {
		EventState state = eventStateService.findById(stateId);

		if (state == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		List<Event> events = state.getEvents();
		return new ResponseEntity<>(events, HttpStatus.OK);
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
	public ResponseEntity<?> updateEvent(@PathVariable String id, @ModelAttribute SaveEventDTO data) {
		Event event = eventServices.findOneById(id);
		if (event == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

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
			eventServices.update(id, data, category, organizer, place);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/change-status")
	public ResponseEntity<?> patchEvent(@ModelAttribute ChangeEventStatusDTO data) {
		Event event = eventServices.findOneById(data.getId());
		if (event == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		try {
			eventServices.changeStatus(data.getId(), data.getStatus());
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
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
		Event event = eventServices.findOneById(id);
		List<Tier> tiers = event.getTiers();
		return new ResponseEntity<>(tiers, HttpStatus.OK);
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