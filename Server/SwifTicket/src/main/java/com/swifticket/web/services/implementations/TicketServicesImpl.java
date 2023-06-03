package com.swifticket.web.services.implementations;

import com.swifticket.web.models.entities.Ticket;
import com.swifticket.web.models.entities.Tier;
import com.swifticket.web.models.entities.User;
import com.swifticket.web.repositories.TicketRepository;
import com.swifticket.web.services.TicketServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TicketServicesImpl implements TicketServices {

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public Ticket findOneById(String ticketId) {
        try {
            UUID id = UUID.fromString(ticketId);
            return ticketRepository.findById(id).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Ticket> findAllByUser(User user) {
        return ticketRepository.findByUser(user);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void create(User user, Tier tier) throws Exception {
        // TODO: validate availability
        Ticket ticket = new Ticket(user, tier);
        ticketRepository.save(ticket);
    }

    @Override
    public String generateCode(Ticket ticket) {
        return null;
    }

    @Override
    public Boolean validateTicket(String verificationToken) {
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public String startTransferTicket(User receiver) throws Exception {
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void acceptTransferTicket(String transferId, User sender, Ticket ticket) throws Exception {

    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void validateTransfer(String code) throws Exception {

    }
}
