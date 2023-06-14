package com.swifticket.web.controllers;

import java.util.List;

import com.swifticket.web.models.dtos.page.PageDTO;
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
@CrossOrigin("*")
public class PlaceController {
	private final PlaceServices placeServices;
	private final ErrorHandler errorHandler;
	@Autowired
	public PlaceController(PlaceServices placeServices, ErrorHandler errorHandler) {
		this.placeServices = placeServices;
		this.errorHandler = errorHandler;
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
