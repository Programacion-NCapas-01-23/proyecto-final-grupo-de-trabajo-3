package com.swifticket.web.services;

import java.util.List;

import com.swifticket.web.models.entities.Organizer;
import org.springframework.data.domain.Page;

public interface OrganizerServices {
	List<Organizer> findAll();
	Page<Organizer> findAll(int page, int size);
	Organizer findById(int id);
	Organizer findOneByName(String name);
	void save(String name) throws Exception;
	void update(int id, String name) throws Exception;
	void delete(int id) throws Exception;
}
