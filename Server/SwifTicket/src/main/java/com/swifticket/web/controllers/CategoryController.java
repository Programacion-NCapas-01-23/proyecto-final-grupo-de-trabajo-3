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
	@Autowired
	public CategoryController(CategoryServices categoryService, ErrorHandler errorHandler) {
		this.categoryService = categoryService;
		this.errorHandler = errorHandler;
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
	public ResponseEntity<?> updateCategory(
			@PathVariable int id, 
			@ModelAttribute @Valid SaveCategoryDTO data,
			BindingResult validations) {
		
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}
		
		Category category = categoryService.findById(id);
		
		if (category == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		try {
			categoryService.update(id, data.getName());
			return new ResponseEntity<>(new MessageDTO("Category updated"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable int id) {
		Category category = categoryService.findById(id);
		
		if (category == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		try {
			categoryService.delete(id);
			return new ResponseEntity<>(new MessageDTO("Category deleted"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
