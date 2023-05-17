package com.swifticket.web.repositories;

import com.swifticket.web.models.entities.Event;
import com.swifticket.web.models.entities.Sponsor;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface SponsorRepository extends ListCrudRepository<Sponsor, Integer> {
    Optional<Sponsor> findByIdAndEvent(String id, Event event);
}
