package com.swifticket.web.repositories;

import com.swifticket.web.models.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
}
