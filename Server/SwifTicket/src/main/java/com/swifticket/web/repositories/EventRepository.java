package com.swifticket.web.repositories;

import com.swifticket.web.models.entities.Event;
import com.swifticket.web.models.entities.Tier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends ListCrudRepository<Event, UUID> {
    List<Tier> findByEventId(UUID eventId);
}
