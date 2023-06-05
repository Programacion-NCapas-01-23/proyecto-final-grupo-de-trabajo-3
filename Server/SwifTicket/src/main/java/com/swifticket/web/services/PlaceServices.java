package com.swifticket.web.services;

import java.util.List;

import com.swifticket.web.models.dtos.place.SavePlaceDTO;
import com.swifticket.web.models.entities.Place;

public interface PlaceServices {
	List<Place> findAll();
	Place findById(int id);
	Place findOneByName(String name);
	Place findOneByAddress(String address);
	void save(SavePlaceDTO data) throws Exception;
	void deleteById(int id) throws Exception;
}
