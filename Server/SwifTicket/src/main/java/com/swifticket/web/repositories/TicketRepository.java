package com.swifticket.web.repositories;

import com.swifticket.web.models.entities.Ticket;
import com.swifticket.web.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    List<Ticket> findByUser(User user);
}
