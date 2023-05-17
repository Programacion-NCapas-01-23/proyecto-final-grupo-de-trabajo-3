package com.swifticket.web.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swifticket.web.models.entities.Category;
import com.swifticket.web.repositories.CategoryRepository;
import com.swifticket.web.services.CategoryServices;

@Service
public class CategoryServiceImpl implements CategoryServices {
	
	@Autowired
	private CategoryRepository repository;

	@Override
	public List<Category> findAll() {
		return repository.findAll();
	}

	@Override
	public void save(String name) {
		Category category = new Category();
		category.setName(name);
		
		repository.save(category);
	}

	@Override
	public void update(int id, String name) {
		Category category = repository.findById(id).orElse(null);
		repository.save(category);
	}

	@Override
	public void delete(int id) {
		repository.deleteById(id);
	}

}