package com.swifticket.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swifticket.web.models.dtos.organizer.SaveOrganizerDTO;
import com.swifticket.web.models.dtos.response.MessageDTO;
import com.swifticket.web.models.entities.Organizer;
import com.swifticket.web.services.OrganizerServices;
import com.swifticket.web.utils.ErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/organizers")
@CrossOrigin("*")
public class OrganizerController {
	private final OrganizerServices organizerServices;
	private final ErrorHandler errorHandler;
	@Autowired
	public OrganizerController(OrganizerServices organizerServices, ErrorHandler errorHandler) {
		this.organizerServices = organizerServices;
		this.errorHandler = errorHandler;
	}

	@GetMapping("")
	public ResponseEntity<?> getOrganizers() {
		List<Organizer> organizers = organizerServices.findAll();
		return new ResponseEntity<>(organizers, HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<?> createOrganizer(
			@ModelAttribute @Valid SaveOrganizerDTO data, 
			BindingResult validations) {
		
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}
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
		
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}
		Organizer organizer = organizerServices.findById(id);
		if (organizer == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		try {
			organizerServices.update(id, data.getName());
			return new ResponseEntity<>(new MessageDTO("organizer updated"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteOrganizer(@PathVariable int id) {
		try {
			organizerServices.delete(id);
			return new ResponseEntity<>(new MessageDTO("organizer deleted"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
