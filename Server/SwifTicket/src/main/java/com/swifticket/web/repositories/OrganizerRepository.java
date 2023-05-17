package com.swifticket.web.repositories;

import com.swifticket.web.models.entities.Organizer;
import org.springframework.data.repository.ListCrudRepository;

public interface OrganizerRepository extends ListCrudRepository<Organizer, Integer> {
}
