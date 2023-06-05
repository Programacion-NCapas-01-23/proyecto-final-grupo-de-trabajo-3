package com.swifticket.web.controllers;

import com.swifticket.web.models.dtos.response.MessageDTO;
import com.swifticket.web.models.dtos.sponsor.SaveSponsorDTO;
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
	public ResponseEntity<?> getSponsors(@RequestParam(required = false) Integer id) {
		if (id != null) {
			Sponsor sponsor = sponsorServices.findById(id);
			if (sponsor == null)
				return new ResponseEntity<>(new MessageDTO("sponsor not found"), HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(sponsor, HttpStatus.OK);
		}
		List<Sponsor> sponsors = sponsorServices.findAll();
		return new ResponseEntity<>(sponsors, HttpStatus.OK);

	}

	@GetMapping("/{name}")
	public ResponseEntity<?> getSponsorByName(@PathVariable String name) {
		Sponsor sponsor = sponsorServices.findByName(name);
		if (sponsor == null)
			return new ResponseEntity<>(new MessageDTO("sponsor not found"), HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(sponsor, HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<?> createSponsor(@ModelAttribute @Valid SaveSponsorDTO data, BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		// check if sponsor already exists
		if (sponsorServices.findByName(data.getName()) != null)
			return new ResponseEntity<>(new MessageDTO("sponsor already exists"), HttpStatus.CONFLICT);

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

		if (sponsorServices.findById(id) == null)
			return new ResponseEntity<>(new MessageDTO("sponsor not found"), HttpStatus.NOT_FOUND);

		// check if sponsor already exists
		if (sponsorServices.findByName(data.getName()) != null && sponsorServices.findOneByNameAndImage(data.getName(), data.getImage()) != null)
			return new ResponseEntity<>(new MessageDTO("sponsor already exists"), HttpStatus.CONFLICT);

		try {
			sponsorServices.update(id, data.getName(), data.getImage());
			return new ResponseEntity<>(new MessageDTO("sponsor updated"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSponsor(@PathVariable int id) {
		Sponsor sponsor = sponsorServices.findById(id);
		if (sponsor == null)
			return new ResponseEntity<>(new MessageDTO("sponsor not found"), HttpStatus.NOT_FOUND);

		try {
			sponsorServices.delete(id);
			return new ResponseEntity<>(new MessageDTO("sponsor deleted"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
