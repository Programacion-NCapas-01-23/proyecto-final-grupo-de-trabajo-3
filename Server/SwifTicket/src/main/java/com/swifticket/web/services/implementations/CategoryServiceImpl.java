package com.swifticket.web.services.implementations;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swifticket.web.models.entities.Category;
import com.swifticket.web.repositories.CategoryRepository;
import com.swifticket.web.services.CategoryServices;

@Service
public class CategoryServiceImpl implements CategoryServices {
	
	private final CategoryRepository repository;
	@Autowired
	public CategoryServiceImpl(CategoryRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Category> findAll() {
		return repository.findAll();
	}
	
	@Override
	public Category findById(int id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Category findByName(String name) {
		return repository.findOneByName(name);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void save(String name) throws Exception{
		Category category = new Category();
		category.setName(name);
		
		repository.save(category);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void update(int id, String name) throws Exception{
		Category category = repository.findById(id).orElse(null);
		assert category != null;

		category.setName(name);
		repository.save(category);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void delete(int id) throws Exception { repository.deleteById(id); }
}
