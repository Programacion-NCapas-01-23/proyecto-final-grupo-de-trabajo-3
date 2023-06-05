package com.swifticket.web.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swifticket.web.models.dtos.place.SavePlaceDTO;
import com.swifticket.web.models.entities.Place;
import com.swifticket.web.repositories.PlaceRepository;
import com.swifticket.web.services.PlaceServices;

import jakarta.transaction.Transactional;

@Service
public class PlaceServiceImpl implements PlaceServices {
	
	private final PlaceRepository repository;
	@Autowired
	public PlaceServiceImpl(PlaceRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Place> findAll() {
		return repository.findAll();
	}

	@Override
	public Place findById(int id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Place findOneByName(String name) {
		return repository.findOneByName(name);
	}

	@Override
	public Place findOneByAddress(String address) {
		return repository.findOneByAddress(address);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void save(SavePlaceDTO data) throws Exception {
		Place place = new Place(data.getName(), data.getAddress());
		repository.save(place);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteById(int id) throws Exception {
		repository.deleteById(id);
	}

}
