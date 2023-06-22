package com.swifticket.web.controllers;

import com.swifticket.web.models.dtos.page.PageDTO;
import com.swifticket.web.models.dtos.response.MessageDTO;
import com.swifticket.web.models.dtos.sponsor.SaveSponsorDTO;
import com.swifticket.web.models.entities.Sponsor;
import com.swifticket.web.services.SponsorServices;
import com.swifticket.web.utils.ErrorHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/sponsors")
@CrossOrigin("*")
public class SponsorController {
	private final SponsorServices sponsorServices;
	private final ErrorHandler errorHandler;
	private final Environment environment;
	@Autowired
	public SponsorController(SponsorServices sponsorServices, ErrorHandler errorHandler, Environment environment) {
		this.sponsorServices = sponsorServices;
		this.errorHandler = errorHandler;
		this.environment = environment;
	}

	private String getSponsorImageUploadPath() {
		return environment.getProperty("sponsor.image.upload.path");
	}

	@GetMapping("")
	public ResponseEntity<?> getSponsors(@RequestParam(defaultValue = "") String name,
										 @RequestParam(defaultValue = "0") int page,
										 @RequestParam(defaultValue = "10") int size) {
		//List<Sponsor> sponsors = sponsorServices.findAll();
		Page<Sponsor> sponsors = sponsorServices.findAll(name, page, size);
		PageDTO<Sponsor> response = new PageDTO<>(
				sponsors.getContent(),
				sponsors.getNumber(),
				sponsors.getSize(),
				sponsors.getTotalElements(),
				sponsors.getTotalPages(),
				sponsors.isEmpty()
		);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping("/{name}")
	public ResponseEntity<?> getSponsorByName(@PathVariable String name) {
		Sponsor sponsor = sponsorServices.findByName(name);
		if (sponsor == null)
			return new ResponseEntity<>(new MessageDTO("sponsor not found"), HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(sponsor, HttpStatus.OK);
	}

	// Post using multipart form data to upload image
	@PostMapping("")
	public ResponseEntity<?> createSponsor(@ModelAttribute @Valid SaveSponsorDTO data,
										   @RequestParam("image") MultipartFile image,
										   BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		// check if sponsor already exists
		if (sponsorServices.findByName(data.getName()) != null)
			return new ResponseEntity<>(new MessageDTO("sponsor already exists"), HttpStatus.CONFLICT);

		try {
			sponsorServices.save(data.getName(), image);
			return new ResponseEntity<>(new MessageDTO("sponsor created"), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Put using multipart form data to change and upload image
	@PutMapping("/{id}")
	public ResponseEntity<?> updateSponsor(@PathVariable int id,
										   @ModelAttribute @Valid SaveSponsorDTO data,
										   @RequestParam("image") MultipartFile image,
										   BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		if (sponsorServices.findById(id) == null)
			return new ResponseEntity<>(new MessageDTO("sponsor not found"), HttpStatus.NOT_FOUND);

		// Check if sponsor already exists
		if (sponsorServices.findByName(data.getName()) != null &&
				sponsorServices.findOneByNameAndImage(data.getName(), data.getImage().getOriginalFilename()) != null) {
			return new ResponseEntity<>(new MessageDTO("sponsor already exists"), HttpStatus.CONFLICT);
		}

		try {
			sponsorServices.update(id, data.getName(), image);
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

		// A sponsor that's assigned to one or more events can't be deleted
		if (sponsor.getEventxSponsors().size() > 0)
			return new ResponseEntity<>(
					new MessageDTO("this sponsor can´t be deleted since it has been assigned"), HttpStatus.CONFLICT);

		try {
			sponsorServices.delete(id);
			return new ResponseEntity<>(new MessageDTO("sponsor deleted"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
