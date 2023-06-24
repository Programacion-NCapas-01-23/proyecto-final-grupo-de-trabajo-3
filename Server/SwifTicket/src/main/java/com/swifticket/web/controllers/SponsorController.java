package com.swifticket.web.controllers;

import com.swifticket.web.models.dtos.page.PageDTO;
import com.swifticket.web.models.dtos.response.MessageDTO;
import com.swifticket.web.models.dtos.sponsor.SaveSponsorDTO;
import com.swifticket.web.models.entities.Sponsor;
import com.swifticket.web.models.entities.User;
import com.swifticket.web.services.SponsorServices;
import com.swifticket.web.services.UserServices;
import com.swifticket.web.utils.ErrorHandler;
import com.swifticket.web.utils.ImageUpload;
import com.swifticket.web.utils.RoleCatalog;
import com.swifticket.web.utils.RoleVerifier;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/sponsors")
public class SponsorController {
	private final SponsorServices sponsorServices;
	private final ErrorHandler errorHandler;
	private final ImageUpload imageUpload;
	private final UserServices userServices;
	@Autowired
	public SponsorController(SponsorServices sponsorServices, ErrorHandler errorHandler, ImageUpload imageUpload, UserServices userServices) {
		this.sponsorServices = sponsorServices;
		this.errorHandler = errorHandler;
		this.imageUpload = imageUpload;
		this.userServices = userServices;
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
		User authUser = userServices.findUserAuthenticated();
		// Grant access by user's role -> ADMIN, SUPER_ADMIN
		int[] validRoles = {RoleCatalog.ADMIN, RoleCatalog.SUPER_ADMIN};
		if (!RoleVerifier.userMatchesRoles(validRoles, userServices.getUserRoles(authUser)))
			return new ResponseEntity<>(new MessageDTO("Credential permissions not valid"), HttpStatus.UNAUTHORIZED);

		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		// check if sponsor already exists
		if (sponsorServices.findByName(data.getName()) != null)
			return new ResponseEntity<>(new MessageDTO("sponsor already exists"), HttpStatus.CONFLICT);

		try {
			String src = imageUpload.uploadImage(image);
			if (src == null)
				return new ResponseEntity<>(new MessageDTO(
						"Failed to save image, please check that the size is less than 5MB and that the uploaded file has an image format"),
						HttpStatus.BAD_REQUEST);
			sponsorServices.save(data.getName(), src);
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
		User authUser = userServices.findUserAuthenticated();
		// Grant access by user's role -> ADMIN, SUPER_ADMIN
		int[] validRoles = {RoleCatalog.ADMIN, RoleCatalog.SUPER_ADMIN};
		if (!RoleVerifier.userMatchesRoles(validRoles, userServices.getUserRoles(authUser)))
			return new ResponseEntity<>(new MessageDTO("Credential permissions not valid"), HttpStatus.UNAUTHORIZED);

		if (validations.hasErrors()) {
			return new ResponseEntity<>(errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		if (sponsorServices.findById(id) == null)
			return new ResponseEntity<>(new MessageDTO("sponsor not found"), HttpStatus.NOT_FOUND);

		try {
			String src = imageUpload.uploadImage(image);
			// Check if sponsor already exists
			if (sponsorServices.findByName(data.getName()) != null &&
					sponsorServices.findOneByNameAndImage(data.getName(), src) != null) {
				return new ResponseEntity<>(new MessageDTO("sponsor already exists"), HttpStatus.CONFLICT);
			}
			if (src == null)
				return new ResponseEntity<>(new MessageDTO(
						"Failed to save image, please check that the size is less than 5MB and that the uploaded file has an image format"),
						HttpStatus.BAD_REQUEST);
			sponsorServices.update(id, data.getName(), src);
			return new ResponseEntity<>(new MessageDTO("sponsor updated"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSponsor(@PathVariable int id) {
		User authUser = userServices.findUserAuthenticated();
		// Grant access by user's role -> ADMIN, SUPER_ADMIN
		int[] validRoles = {RoleCatalog.ADMIN, RoleCatalog.SUPER_ADMIN};
		if (!RoleVerifier.userMatchesRoles(validRoles, userServices.getUserRoles(authUser)))
			return new ResponseEntity<>(new MessageDTO("Credential permissions not valid"), HttpStatus.UNAUTHORIZED);

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
