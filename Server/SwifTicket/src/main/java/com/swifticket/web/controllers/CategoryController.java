package com.swifticket.web.controllers;

import java.util.List;

import com.swifticket.web.models.entities.User;
import com.swifticket.web.services.UserServices;
import com.swifticket.web.utils.RoleCatalog;
import com.swifticket.web.utils.RoleVerifier;
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

import com.swifticket.web.models.dtos.category.SaveCategoryDTO;
import com.swifticket.web.models.dtos.response.MessageDTO;
import com.swifticket.web.models.entities.Category;
import com.swifticket.web.services.CategoryServices;
import com.swifticket.web.utils.ErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
@CrossOrigin("*")
public class CategoryController {
	
	private final CategoryServices categoryService;
	private final ErrorHandler errorHandler;
	private final UserServices userServices;
	@Autowired
	public CategoryController(CategoryServices categoryService, ErrorHandler errorHandler, UserServices userServices) {
		this.categoryService = categoryService;
		this.errorHandler = errorHandler;
		this.userServices = userServices;
	}

	@GetMapping("")
	public ResponseEntity<?> getCategories() {
		List<Category> categories = categoryService.findAll();
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<?> createCategory(
			@ModelAttribute @Valid SaveCategoryDTO data,
			BindingResult validations) {
		User authUser = userServices.findUserAuthenticated();
		// Grant access by user's role -> ADMIN, SUPER_ADMIN
		int[] validRoles = {RoleCatalog.ADMIN, RoleCatalog.SUPER_ADMIN};
		if (!RoleVerifier.userMatchesRoles(validRoles, userServices.getUserRoles(authUser)))
			return new ResponseEntity<>(new MessageDTO("Credential permissions not valid"), HttpStatus.UNAUTHORIZED);

		// check if category already exists
		Category category = categoryService.findByName(data.getName());
		if (category != null)
			return new ResponseEntity<>(new MessageDTO("Category already exists"), HttpStatus.CONFLICT);

		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}
		
		try {
			categoryService.save(data.getName());
			return new ResponseEntity<>(new MessageDTO("Category created"), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCategory(@PathVariable int id,
											@ModelAttribute @Valid SaveCategoryDTO data,
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

		if (categoryService.findById(id) == null)
			return new ResponseEntity<>(new MessageDTO("Category not found"), HttpStatus.NOT_FOUND);

		// check if category already exists;
		if (categoryService.findByName(data.getName()) != null)
			return new ResponseEntity<>(new MessageDTO("Category already exists"), HttpStatus.CONFLICT);

		try {
			categoryService.update(id, data.getName());
			return new ResponseEntity<>(new MessageDTO("Category updated"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable int id) {
		User authUser = userServices.findUserAuthenticated();
		// Grant access by user's role -> ADMIN, SUPER_ADMIN
		int[] validRoles = {RoleCatalog.ADMIN, RoleCatalog.SUPER_ADMIN};
		if (!RoleVerifier.userMatchesRoles(validRoles, userServices.getUserRoles(authUser)))
			return new ResponseEntity<>(new MessageDTO("Credential permissions not valid"), HttpStatus.UNAUTHORIZED);

		Category category = categoryService.findById(id);
		if (category == null)
			return new ResponseEntity<>(new MessageDTO("Category not found"), HttpStatus.NOT_FOUND);

		// A category with events can't be deleted
		if (category.getEvents().size() > 0)
			return new ResponseEntity<>(
					new MessageDTO("this category can´t be deleted since it has been assigned"), HttpStatus.CONFLICT);

		try {
			categoryService.delete(id);
			return new ResponseEntity<>(new MessageDTO("Category deleted"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
