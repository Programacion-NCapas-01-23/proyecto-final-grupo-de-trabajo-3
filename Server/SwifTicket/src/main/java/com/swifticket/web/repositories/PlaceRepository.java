package com.swifticket.web.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.swifticket.web.models.entities.Place;

public interface PlaceRepository extends JpaRepository<Place, Integer> {
    Place findOneByName(String name);
    Place findOneByAddress(String address);
    Page<Place> findByNameContains(String name, Pageable pageable);
}
