package com.swifticket.web.services;

import java.util.List;

import com.swifticket.web.models.entities.Sponsor;

public interface SponsorServices {
	List<Sponsor> findAll();
	void save(String name, String image);
	void update(int id, String name, String image);
	void delete(int id);
}
