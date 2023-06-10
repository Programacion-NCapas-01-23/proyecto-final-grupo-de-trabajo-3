package com.swifticket.web.services;

import java.util.List;

import com.swifticket.web.models.entities.Organizer;

public interface OrganizerServices {
	List<Organizer> findAll();
	Organizer findById(int id);
	Organizer findOneByName(String name);
	void save(String name) throws Exception;
	void update(int id, String name) throws Exception;
	void delete(int id) throws Exception;
}
