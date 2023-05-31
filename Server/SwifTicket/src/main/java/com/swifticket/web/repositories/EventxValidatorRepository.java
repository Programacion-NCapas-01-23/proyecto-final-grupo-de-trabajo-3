package com.swifticket.web.repositories;

import com.swifticket.web.models.entities.Event;
import com.swifticket.web.models.entities.EventxValidator;
import com.swifticket.web.models.entities.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface EventxValidatorRepository extends ListCrudRepository<EventxValidator, UUID> {
    EventxValidator findOneByEventAndUser(Event event, User user);
}
