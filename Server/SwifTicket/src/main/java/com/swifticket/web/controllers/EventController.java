package com.swifticket.web.controllers;

import java.util.List;

import com.swifticket.web.models.dtos.event.*;
import com.swifticket.web.models.dtos.page.PageDTO;
import com.swifticket.web.models.dtos.response.MessageAndSoldTicketsDTO;
import com.swifticket.web.models.dtos.response.MessageDTO;
import com.swifticket.web.models.dtos.tier.SaveTierDTO;
import com.swifticket.web.models.dtos.tier.UpdateTierDTO;
import com.swifticket.web.models.entities.*;
import com.swifticket.web.services.*;
import com.swifticket.web.utils.DateValidator;
import com.swifticket.web.utils.ErrorHandler;
import com.swifticket.web.utils.ImageUpload;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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
	private final TierServices tierServices;
	private final DateValidator dateValidator;
	private final int PROGRAMMED = 1;
	private final ImageUpload imageUpload;

	@Autowired
	public EventController(EventServices eventServices, CategoryServices categoryServices,
						   PlaceServices placeService, OrganizerServices organizerService,
						   EventStateServices eventStateService, ErrorHandler errorHandler,
						   SponsorServices sponsorServices, TierServices tierServices,
						   DateValidator dateValidator, ImageUpload imageUpload) {
		this.eventServices = eventServices;
		this.categoryServices = categoryServices;
		this.placeService = placeService;
		this.organizerService = organizerService;
		this.eventStateService = eventStateService;
		this.errorHandler = errorHandler;
		this.sponsorServices = sponsorServices;
		this.tierServices = tierServices;
		this.dateValidator = dateValidator;
		this.imageUpload = imageUpload;
	}

	@GetMapping("")
	public ResponseEntity<?> getEvents(@RequestParam(defaultValue = "") String title,
									   @RequestParam(defaultValue = "0") int page,
									   @RequestParam(defaultValue = "10") int size) {
		Page<Event> events = eventServices.findAll(title, page, size);
		PageDTO<Event> response = new PageDTO<>(
				events.getContent(),
				events.getNumber(),
				events.getSize(),
				events.getTotalElements(),
				events.getTotalPages(),
				events.isEmpty()
		);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getEvent(@PathVariable String id) {
		Event event = eventServices.findById(id);
		if (event == null)
			return new ResponseEntity<>(new MessageDTO("Event not found"), HttpStatus.NOT_FOUND);

		List<EventxSponsor> relations = event.getEventSponsors();
		List<Sponsor> sponsors = relations.stream().map(EventxSponsor::getSponsor).toList();

		EventWithSponsorsDTO _event = new EventWithSponsorsDTO(event, sponsors);
		return new ResponseEntity<>(_event, HttpStatus.OK);
	}

	@GetMapping("/category/{categoryId}")
	public ResponseEntity<?> getEventByCategory(@PathVariable int categoryId) {
		Category category = categoryServices.findById(categoryId);

		if (category == null)
			return new ResponseEntity<>(new MessageDTO("category not found"), HttpStatus.NOT_FOUND);

		List<Event> events = category.getEvents();
		return new ResponseEntity<>(events, HttpStatus.OK);
	}

	@GetMapping("/state/{stateId}")
	public ResponseEntity<?> getEventByState(@PathVariable int stateId) {
		EventState state = eventStateService.findById(stateId);

		if (state == null)
			return new ResponseEntity<>(new MessageDTO("event state not found"), HttpStatus.NOT_FOUND);

		List<Event> events = state.getEvents();
		return new ResponseEntity<>(events, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<?> createEvent(@ModelAttribute @Valid SaveEventDTO data,
										 @RequestParam("image") MultipartFile image,
										 BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		Category category = categoryServices.findById(data.getCategoryId());
		if (category == null)
			return new ResponseEntity<>(new MessageDTO("category not found"), HttpStatus.NOT_FOUND);

		Place place = placeService.findById(data.getPlaceId());
		if (place == null)
			return new ResponseEntity<>(new MessageDTO("place not found"), HttpStatus.NOT_FOUND);

		Organizer organizer = organizerService.findById(data.getOrganizerId());
		if (organizer == null)
			return new ResponseEntity<>(new MessageDTO("organizer not found"), HttpStatus.NOT_FOUND);

		EventState state = eventStateService.findById(PROGRAMMED);
		if (state == null)
			return new ResponseEntity<>(new MessageDTO("event state not found"), HttpStatus.NOT_FOUND);

		// Validate event date
		if (dateValidator.isValidEventDate(data.getDateTime()))
			return new ResponseEntity<>(new MessageDTO("Cannot add events on past dates"), HttpStatus.CONFLICT);

		try {
			String src = imageUpload.uploadImage(image);
			if (src == null)
				return new ResponseEntity<>(new MessageDTO(
						"Failed to save image, please check that the size is less than 5MB and that the uploaded file has an image format"),
						HttpStatus.BAD_REQUEST);
			data.setSrc(src);
			eventServices.save(data, category, organizer, place, state);
			return new ResponseEntity<>(new MessageDTO("event created"), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateEvent(@PathVariable String id,
										 @ModelAttribute @Valid SaveEventDTO data,
										 @RequestParam(value = "image", required = false) MultipartFile image,
										 BindingResult bindingResult) {
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

		// Validate event date
		if (dateValidator.isValidEventDate(data.getDateTime()))
			return new ResponseEntity<>(new MessageDTO("Cannot add events on past dates"), HttpStatus.CONFLICT);

		try {
			String src = imageUpload.uploadImage(image);
			if (src == null)
				return new ResponseEntity<>(new MessageDTO(
						"Failed to save image, please check that the size is less than 5MB and that the uploaded file has an image format"),
						HttpStatus.BAD_REQUEST);
			data.setSrc(src);
			eventServices.update(id, data, category, organizer, place);
			return new ResponseEntity<>(new MessageDTO("event updated"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/change-status")
	public ResponseEntity<?> changeEventStatus(
			@ModelAttribute @Valid ChangeEventStatusDTO data, BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		Event event = eventServices.findById(data.getEventId());
		if (event == null)
			return new ResponseEntity<>(new MessageDTO("event not found"), HttpStatus.NOT_FOUND);

		EventState state = eventStateService.findById(data.getStatusId());
		if (state == null)
			return new ResponseEntity<>(new MessageDTO("event state not found"), HttpStatus.NOT_FOUND);

		try {
			eventServices.changeStatus(event, state);
			return new ResponseEntity<>(new MessageDTO("status changed"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/sponsors")
	public ResponseEntity<?> assignSponsor(@ModelAttribute @Valid AssignSponsorToEventDTO data, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		Event event = eventServices.findById(data.getEventId());
		if (event == null)
			return new ResponseEntity<>(new MessageDTO("Event not found"), HttpStatus.NOT_FOUND);

		Sponsor sponsor = sponsorServices.findByName(data.getSponsorName());
		if (sponsor == null)
			return new ResponseEntity<>(new MessageDTO("Sponsor not found"), HttpStatus.NOT_FOUND);

		EventxSponsor relation = eventServices.findByEventAndSponsor(event, sponsor);
		if (relation != null)
			return new ResponseEntity<>(new MessageDTO("Sponsor is already assigned to event"), HttpStatus.OK);

		try {
			eventServices.assignSponsor(event, sponsor);
			return new ResponseEntity<>(new MessageDTO("Sponsor assigned successfully"), HttpStatus.OK);
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

		Event event = eventServices.findById(data.getEventId());
		if (event == null)
			return new ResponseEntity<>(new MessageDTO("Event not found"), HttpStatus.NOT_FOUND);

		Sponsor sponsor = sponsorServices.findById(data.getSponsorId());
		if (sponsor == null)
			return new ResponseEntity<>(new MessageDTO("Sponsor not found"), HttpStatus.NOT_FOUND);

		EventxSponsor relation = eventServices.findByEventAndSponsor(event, sponsor);
		if (relation == null)
			return new ResponseEntity<>(new MessageDTO("Sponsor was not assigned to event"), HttpStatus.OK);

		try {
			eventServices.removeSponsor(event, sponsor);
			return new ResponseEntity<>(new MessageDTO("Sponsor removed successfully"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}/tiers")
	public ResponseEntity<?> getEventTiers(@PathVariable String id) {
		Event event = eventServices.findById(id);
		if (event == null)
			return new ResponseEntity<>(new MessageDTO("event not found"), HttpStatus.NOT_FOUND);

		List<Tier> tiers = event.getTiers();
		return new ResponseEntity<>(tiers, HttpStatus.OK);
	}

	@PostMapping("/tiers")
	public ResponseEntity<?> createEventTier(@ModelAttribute @Valid SaveTierDTO data, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		Event event = eventServices.findById(data.getEventId());
		if (event == null)
			return new ResponseEntity<>(new MessageDTO("event not found"), HttpStatus.NOT_FOUND);

		try {
			eventServices.createTier(data);
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

		Tier tier = tierServices.findById(tierId);
		if (tier == null)
			return new ResponseEntity<>(new MessageDTO("Tier not found"), HttpStatus.NOT_FOUND);

		// Validate capacity availability
		int ticketsSold = tier.getTickets().size();
		if (ticketsSold > data.getCapacity())
			return new ResponseEntity<>(
					new MessageAndSoldTicketsDTO("more tickets have been sold than the new capacity", ticketsSold),
					HttpStatus.CONFLICT);

		try {
			eventServices.updateTier(tierId, data);
			return new ResponseEntity<>(new MessageDTO("Tier updated"), HttpStatus.OK);
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

		// Validate if tier can be deleted
		int ticketsSold = tier.getTickets().size();
		if (ticketsSold > 0)
			return new ResponseEntity<>(
					new MessageAndSoldTicketsDTO("can't delete a tier with sold tickets", ticketsSold),
					HttpStatus.CONFLICT);

		try {
			eventServices.deleteTier(tierId);
			return new ResponseEntity<>(new MessageDTO("Tier deleted"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}