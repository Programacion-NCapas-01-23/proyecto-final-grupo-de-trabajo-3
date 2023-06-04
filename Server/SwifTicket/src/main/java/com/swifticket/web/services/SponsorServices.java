package com.swifticket.web.services;

import java.util.List;

import com.swifticket.web.models.entities.Organizer;
import com.swifticket.web.models.entities.Sponsor;

public interface SponsorServices {
	List<Sponsor> findAll();
	Sponsor findById(int id);
	Sponsor findByName(String name);
	void save(String name, String image) throws Exception;
	void update(int id, String name, String image) throws Exception;
	void delete(int id) throws Exception;
}
