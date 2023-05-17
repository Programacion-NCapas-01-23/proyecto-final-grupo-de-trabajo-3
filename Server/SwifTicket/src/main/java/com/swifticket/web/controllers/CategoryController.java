package com.swifticket.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.swifticket.web.models.entities.Category;
import com.swifticket.web.services.CategoryServices;

@RestController
@RequestMapping("/categories")
@CrossOrigin("*")
public class CategoryController {
	
	@Autowired
	private CategoryServices categoryService;

	@GetMapping("")
	public ResponseEntity<?> getCategories() {
		List<Category> categories = categoryService.findAll();
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<?> createCategory(@ModelAttribute SaveCategoryDTO data) {
		categoryService.save(data.getName());
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCategory(@PathVariable String id) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable String id) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
