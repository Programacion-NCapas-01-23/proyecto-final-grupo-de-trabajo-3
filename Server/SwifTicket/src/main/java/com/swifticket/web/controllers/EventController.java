package com.swifticket.web.controllers;

import java.util.List;

import com.swifticket.web.models.dtos.event.RemoveSponsorFromEventDTO;
import com.swifticket.web.models.dtos.response.MessageDTO;
import com.swifticket.web.models.dtos.sponsor.SaveSponsorDTO;
import com.swifticket.web.models.dtos.tier.SaveTierDTO;
import com.swifticket.web.models.dtos.tier.UpdateTierDTO;
import com.swifticket.web.services.*;
import com.swifticket.web.services.implementations.SponsorServicesImpl;
import com.swifticket.web.utils.ErrorHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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


@RestController
@RequestMapping("/events")
@CrossOrigin("*")
public class EventController {

	private final EventServices eventServices;
	private final CategoryServices categoryServices;
	private final PlaceServices placeService;
	private final OrganizerServices organizerService;
	private final EventStateServices eventStateService;
	private final ErrorHandler errorHandler;
	private final SponsorServices sponsorServices;
	private TierServices tierServices;

	@Autowired
	public EventController(EventServices eventServices, CategoryServices categoryServices, PlaceServices placeService, OrganizerServices organizerService, EventStateServices eventStateService, ErrorHandler errorHandler, SponsorServices sponsorServices) {
		this.eventServices = eventServices;
		this.categoryServices = categoryServices;
		this.placeService = placeService;
		this.organizerService = organizerService;
		this.eventStateService = eventStateService;
		this.errorHandler = errorHandler;
		this.sponsorServices = sponsorServices;
	}

	@GetMapping("")
	public ResponseEntity<?> getEvents() {
		List<Event> events = eventServices.findAll();
		return new ResponseEntity<>(events, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getEvent(@PathVariable String id) {
		Event event = eventServices.findOneById(id);
		return new ResponseEntity<>(event, HttpStatus.OK);
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
	public ResponseEntity<?> createEvent(@ModelAttribute @Valid SaveEventDTO data, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}
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
	public ResponseEntity<?> updateEvent(@PathVariable String id, @ModelAttribute @Valid SaveEventDTO data, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		Event event = eventServices.findById(id);
		if (event == null) {
			return new ResponseEntity<>(new MessageDTO("Event not found"), HttpStatus.NOT_FOUND);
		}

		Category category = categoryServices.findById(data.getCategoryId());
		if (category == null) {
			return new ResponseEntity<>(new MessageDTO("Category not found"), HttpStatus.NOT_FOUND);
		}

		Place place = placeService.findById(data.getPlaceId());
		if (place == null) {
			return new ResponseEntity<>(new MessageDTO("Place not found"), HttpStatus.NOT_FOUND);
		}

		Organizer organizer = organizerService.findById(data.getOrganizerId());
		if (organizer == null) {
			return new ResponseEntity<>(new MessageDTO("Organizer not found"), HttpStatus.NOT_FOUND);
		}


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
			return new ResponseEntity<>(new MessageDTO("Status changed"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PostMapping("/sponsors")
	public ResponseEntity<?> assignSponsor(@ModelAttribute @Valid SaveSponsorDTO data, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}
		try {
			eventServices.assignSponsor(data.getEventId(), data);
			return new ResponseEntity<>(new MessageDTO("Sponsor assigned"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@DeleteMapping("/sponsors")
	public ResponseEntity<?> removeSponsor(@ModelAttribute @Valid RemoveSponsorFromEventDTO data, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		if (eventServices.findById(data.getEventId()) == null) {
			return new ResponseEntity<>(new MessageDTO("Event not found"), HttpStatus.NOT_FOUND);
		}

		if (sponsorServices.findById(data.getSponsorId()) == null) {
			return new ResponseEntity<>(new MessageDTO("Sponsor not found"), HttpStatus.NOT_FOUND);
		}

		try {
			eventServices.removeSponsor(data.getEventId(), data.getSponsorId());
			return new ResponseEntity<>(new MessageDTO("Sponsor removed"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}/tiers")
	public ResponseEntity<?> getEventTiers(@PathVariable String id) {
		Event event = eventServices.findById(id);
		List<Tier> tiers = event.getTiers();
		return new ResponseEntity<>(tiers, HttpStatus.OK);
	}

	@PostMapping("/tiers")
	public ResponseEntity<?> createEventTier(@ModelAttribute @Valid SaveTierDTO data, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}
		try {
			eventServices.createTier(data.getEventId(), data);
			return new ResponseEntity<>(new MessageDTO("Tier has been added"), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/tiers/{tierId}")
	public ResponseEntity<?> updateEventTier(@PathVariable String tierId, @ModelAttribute @Valid UpdateTierDTO data, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}
		/*
		Event event = eventServices.findById(data.getEventId());
		if (event == null) {
			return new ResponseEntity<>(new MessageDTO("Event not found"), HttpStatus.NOT_FOUND);
		}
		*/
		Tier tier = tierServices.findById(tierId);
		if (tier == null) {
			return new ResponseEntity<>(new MessageDTO("Tier not found"), HttpStatus.NOT_FOUND);
		}

		try {
			eventServices.updateTier(tierId, data);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/tiers/{tierId}")
	public ResponseEntity<?> deleteEventTier(@PathVariable String tierId) {
		Tier tier = tierServices.findById(tierId);
		if (tier == null) {
			return new ResponseEntity<>(new MessageDTO("Tier not found"), HttpStatus.NOT_FOUND);
		}

		try {
			eventServices.deleteTier(tierId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}