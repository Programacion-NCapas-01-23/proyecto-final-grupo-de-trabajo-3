package com.swifticket.web.repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.swifticket.web.models.entities.Place;

public interface PlaceRepository extends ListCrudRepository<Place, Integer> {

}
