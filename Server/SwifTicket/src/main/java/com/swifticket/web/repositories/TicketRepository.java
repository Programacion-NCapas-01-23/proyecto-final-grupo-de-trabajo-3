package com.swifticket.web.repositories;

import com.swifticket.web.models.entities.Ticket;
import com.swifticket.web.models.entities.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends ListCrudRepository<Ticket, UUID> {
    List<Ticket> findByUser(User user);
}
