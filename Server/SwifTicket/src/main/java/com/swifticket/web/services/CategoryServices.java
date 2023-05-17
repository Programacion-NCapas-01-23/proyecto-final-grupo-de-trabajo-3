package com.swifticket.web.services;

import java.util.List;

import com.swifticket.web.models.entities.Category;

public interface CategoryServices {
	List<Category> findAll();
	Category findById(int id);
	void save(String name);
	void update(int id, String name);
	void delete(int id);
}
