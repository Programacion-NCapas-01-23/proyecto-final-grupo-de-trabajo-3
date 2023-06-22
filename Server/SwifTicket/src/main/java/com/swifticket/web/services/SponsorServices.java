package com.swifticket.web.services;

import java.util.List;

import com.swifticket.web.models.entities.Sponsor;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface SponsorServices {
	List<Sponsor> findAll();
	Page<Sponsor> findAll(String name, int page, int size);
	Sponsor findById(int id);
	Sponsor findByName(String name);
	Sponsor findOneByNameAndImage(String name, String image);
	void save(String name, String image) throws Exception;
	void update(int id, String name, MultipartFile image) throws Exception;
	void delete(int id) throws Exception;
}
