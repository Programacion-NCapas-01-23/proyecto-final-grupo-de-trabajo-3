package com.swifticket.web.controllers;

import com.swifticket.web.models.dtos.page.PageDTO;
import com.swifticket.web.models.entities.User;
import com.swifticket.web.services.UserServices;
import com.swifticket.web.utils.RoleCatalog;
import com.swifticket.web.utils.RoleVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.swifticket.web.models.dtos.organizer.SaveOrganizerDTO;
import com.swifticket.web.models.dtos.response.MessageDTO;
import com.swifticket.web.models.entities.Organizer;
import com.swifticket.web.services.OrganizerServices;
import com.swifticket.web.utils.ErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/organizers")
public class OrganizerController {
	private final OrganizerServices organizerServices;
	private final ErrorHandler errorHandler;
	private final UserServices userServices;
	@Autowired
	public OrganizerController(OrganizerServices organizerServices, ErrorHandler errorHandler, UserServices userServices) {
		this.organizerServices = organizerServices;
		this.errorHandler = errorHandler;
		this.userServices = userServices;
	}

	@GetMapping("")
	public ResponseEntity<?> getOrganizers(@RequestParam(defaultValue = "") String name,
										   @RequestParam(defaultValue = "0") int page,
										   @RequestParam(defaultValue = "10") int size){
		// List<Organizer> organizers = organizerServices.findAll();
		Page<Organizer> organizers = organizerServices.findAll(name, page, size);
		PageDTO<Organizer> response = new PageDTO<>(
				organizers.getContent(),
				organizers.getNumber(),
				organizers.getSize(),
				organizers.getTotalElements(),
				organizers.getTotalPages(),
				organizers.isEmpty()
		);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<?> createOrganizer(
			@ModelAttribute @Valid SaveOrganizerDTO data, 
			BindingResult validations) {
		User authUser = userServices.findUserAuthenticated();
		// Grant access by user's role -> ADMIN, SUPER_ADMIN
		int[] validRoles = {RoleCatalog.ADMIN, RoleCatalog.SUPER_ADMIN};
		if (!RoleVerifier.userMatchesRoles(validRoles, userServices.getUserRoles(authUser)))
			return new ResponseEntity<>(new MessageDTO("Credential permissions not valid"), HttpStatus.UNAUTHORIZED);
		
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		if(organizerServices.findOneByName(data.getName()) != null)
			return new ResponseEntity<>(new MessageDTO("organizer already exists"), HttpStatus.CONFLICT);

		try {
			organizerServices.save(data.getName());
			return new ResponseEntity<>(new MessageDTO("organizer created"), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateOrganizer(
			@PathVariable int id, 
			@ModelAttribute @Valid SaveOrganizerDTO data, 
			BindingResult validations) {
		User authUser = userServices.findUserAuthenticated();
		// Grant access by user's role -> ADMIN, SUPER_ADMIN
		int[] validRoles = {RoleCatalog.ADMIN, RoleCatalog.SUPER_ADMIN};
		if (!RoleVerifier.userMatchesRoles(validRoles, userServices.getUserRoles(authUser)))
			return new ResponseEntity<>(new MessageDTO("Credential permissions not valid"), HttpStatus.UNAUTHORIZED);
		
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		if (organizerServices.findById(id) == null)
			return new ResponseEntity<>(new MessageDTO("organizer not found"), HttpStatus.NOT_FOUND);

		try {
			organizerServices.update(id, data.getName());
			return new ResponseEntity<>(new MessageDTO("organizer updated"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteOrganizer(@PathVariable int id) {
		User authUser = userServices.findUserAuthenticated();
		// Grant access by user's role -> ADMIN, SUPER_ADMIN
		int[] validRoles = {RoleCatalog.ADMIN, RoleCatalog.SUPER_ADMIN};
		if (!RoleVerifier.userMatchesRoles(validRoles, userServices.getUserRoles(authUser)))
			return new ResponseEntity<>(new MessageDTO("Credential permissions not valid"), HttpStatus.UNAUTHORIZED);

		Organizer organizer = organizerServices.findById(id);
		if (organizer == null)
			return new ResponseEntity<>(new MessageDTO("organizer not found"), HttpStatus.NOT_FOUND);

		// An organizer that's assigned to one or more events can't be deleted
		if (organizer.getEvents().size() > 0)
			return new ResponseEntity<>(
					new MessageDTO("this organizer can´t be deleted since it has been assigned"), HttpStatus.CONFLICT);

		try {
			organizerServices.delete(id);
			return new ResponseEntity<>(new MessageDTO("organizer deleted"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
