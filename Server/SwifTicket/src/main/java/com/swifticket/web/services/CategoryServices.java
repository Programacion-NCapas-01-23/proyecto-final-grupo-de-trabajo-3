package com.swifticket.web.services;

import java.util.List;

import com.swifticket.web.models.entities.Category;

public interface CategoryServices {
	List<Category> findAll();
	void save(String name);
	void update(String id, String name);
	void delete(String id);
}
