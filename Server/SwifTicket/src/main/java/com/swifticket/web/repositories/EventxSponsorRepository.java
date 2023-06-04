package com.swifticket.web.repositories;

import com.swifticket.web.models.entities.Event;
import com.swifticket.web.models.entities.EventxSponsor;
import com.swifticket.web.models.entities.Sponsor;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface EventxSponsorRepository extends ListCrudRepository<EventxSponsor, UUID> {
    EventxSponsor findOneByEventAndSponsor(Event event, Sponsor sponsor);
}
