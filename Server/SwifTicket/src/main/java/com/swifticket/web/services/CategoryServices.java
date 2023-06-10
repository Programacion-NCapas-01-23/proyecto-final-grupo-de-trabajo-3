package com.swifticket.web.services;

import java.util.List;

import com.swifticket.web.models.entities.Category;

public interface CategoryServices {
	List<Category> findAll();
	Category findById(int id);
	Category findByName(String name);
	void save(String name) throws Exception;
	void update(int id, String name) throws Exception;
	void delete(int id) throws Exception;
}
