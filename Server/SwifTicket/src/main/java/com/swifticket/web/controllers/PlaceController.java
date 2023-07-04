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

import com.swifticket.web.models.dtos.place.SavePlaceDTO;
import com.swifticket.web.models.dtos.response.MessageDTO;
import com.swifticket.web.models.entities.Place;
import com.swifticket.web.services.PlaceServices;
import com.swifticket.web.utils.ErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/places")
public class PlaceController {
	private final PlaceServices placeServices;
	private final ErrorHandler errorHandler;
	private final UserServices userServices;
	@Autowired
	public PlaceController(PlaceServices placeServices, ErrorHandler errorHandler, UserServices userServices) {
		this.placeServices = placeServices;
		this.errorHandler = errorHandler;
		this.userServices = userServices;
	}

	@GetMapping("")
	public ResponseEntity<?> getPlaces(@RequestParam(defaultValue = "") String name,
									   @RequestParam(defaultValue = "0") int page,
									   @RequestParam(defaultValue = "10") int size) {
		// List<Place> places = placeServices.findAll();
		Page<Place> places = placeServices.findAll(name, page, size);
		PageDTO<Place> response = new PageDTO<>(
				places.getContent(),
				places.getNumber(),
				places.getSize(),
				places.getTotalElements(),
				places.getTotalPages(),
				places.isEmpty()
		);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getPlace(@PathVariable int id) {
		Place place = placeServices.findById(id);
		
		if (place == null)
			return new ResponseEntity<>(new MessageDTO("place not found"), HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(place, HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<?> createPlace(
			@ModelAttribute @Valid SavePlaceDTO data, 
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

		// Verify if place already exists
		Place place = placeServices.findOneByAddress(data.getAddress());
		if (place != null)
			return new ResponseEntity<>(new MessageDTO("place with that exactly address already exists"), HttpStatus.CONFLICT);
		
		try {
			placeServices.save(data);
			return new ResponseEntity<>(new MessageDTO("place created"), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePlace(@PathVariable int id) {
		User authUser = userServices.findUserAuthenticated();
		// Grant access by user's role -> ADMIN, SUPER_ADMIN
		int[] validRoles = {RoleCatalog.ADMIN, RoleCatalog.SUPER_ADMIN};
		if (!RoleVerifier.userMatchesRoles(validRoles, userServices.getUserRoles(authUser)))
			return new ResponseEntity<>(new MessageDTO("Credential permissions not valid"), HttpStatus.UNAUTHORIZED);

		Place place = placeServices.findById(id);
		
		if (place == null)
			return new ResponseEntity<>(new MessageDTO("place not found"), HttpStatus.NOT_FOUND);
		
		try {
			placeServices.deleteById(id);
			return new ResponseEntity<>(new MessageDTO("place deleted"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
