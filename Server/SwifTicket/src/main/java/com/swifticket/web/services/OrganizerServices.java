package com.swifticket.web.services;

import java.util.List;

import com.swifticket.web.models.entities.Organizer;

public interface OrganizerServices {
	List<Organizer> findAll();
	void save(String name);
	void update(int id, String name);

	void delete(int id);
}
