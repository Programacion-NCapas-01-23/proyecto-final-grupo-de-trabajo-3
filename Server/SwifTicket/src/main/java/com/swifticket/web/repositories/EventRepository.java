package com.swifticket.web.repositories;

import com.swifticket.web.models.entities.Event;
import org.springframework.data.repository.ListCrudRepository;
import java.util.UUID;

public interface EventRepository extends ListCrudRepository<Event, UUID> {

}
