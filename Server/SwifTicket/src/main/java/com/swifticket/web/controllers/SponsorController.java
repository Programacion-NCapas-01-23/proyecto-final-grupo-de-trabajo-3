package com.swifticket.web.controllers;

import com.swifticket.web.models.dtos.response.MessageDTO;
import com.swifticket.web.models.dtos.sponsor.SaveSponsorDTO;
import com.swifticket.web.models.entities.Organizer;
import com.swifticket.web.models.entities.Sponsor;
import com.swifticket.web.services.SponsorServices;
import com.swifticket.web.utils.ErrorHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sponsors")
@CrossOrigin("*")
public class SponsorController {
	private final SponsorServices sponsorServices;
	private final ErrorHandler errorHandler;
	@Autowired
	public SponsorController(SponsorServices sponsorServices, ErrorHandler errorHandler) {
		this.sponsorServices = sponsorServices;
		this.errorHandler = errorHandler;
	}

	@GetMapping("")
	public ResponseEntity<?> getSponsors() {
		List<Sponsor> sponsors = sponsorServices.findAll();
		return new ResponseEntity<>(sponsors, HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<?> createSponsor(@ModelAttribute @Valid SaveSponsorDTO data, BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}
		try {
			sponsorServices.save(data.getName(), data.getImage());
			return new ResponseEntity<>(new MessageDTO("sponsor created"), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateSponsor(@PathVariable int id, @ModelAttribute @Valid SaveSponsorDTO data, BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}
		Sponsor sponsor = sponsorServices.findById(id);
		if (sponsor == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		try {
			sponsorServices.update(id, data.getName(), data.getImage());
			return new ResponseEntity<>(new MessageDTO("sponsor updated"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSponsor(@PathVariable int id) {
		try{
			sponsorServices.delete(id);
			return new ResponseEntity<>(new MessageDTO("sponsor deleted"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
