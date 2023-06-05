package com.swifticket.web.services.implementations;

import com.swifticket.web.models.entities.*;
import com.swifticket.web.repositories.TicketRepository;
import com.swifticket.web.repositories.TokenRepository;
import com.swifticket.web.repositories.TransactionRepository;
import com.swifticket.web.services.TicketServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TicketServicesImpl implements TicketServices {

    private final TicketRepository ticketRepository;
    private final TokenRepository tokenRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public TicketServicesImpl(TicketRepository ticketRepository, TokenRepository tokenRepository, TransactionRepository transactionRepository) {
        this.ticketRepository = ticketRepository;
        this.tokenRepository = tokenRepository;
        this.transactionRepository = transactionRepository;
    }

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
        Ticket ticket = new Ticket(user, tier);
        ticketRepository.save(ticket);
    }

    @Override
    public Boolean isTicketUsed(Ticket ticket) {
        // Validate if ticket was not used before
        List<Token> tokens = ticket.getTokens();
        final Boolean[] wasUsed = {false};
        tokens.forEach(t -> {
            if (t.getVerifiedAt() != null)
                wasUsed[0] = true;
        });

        return wasUsed[0];
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public String generateUseTicketCode(Ticket ticket) throws Exception {
        Date today = new Date();
        // Expiration date is set to 10 minutes
        Date expirationDate = new Date(today.getTime() + (1000 * 60 * 10));

        // Validate if ticket was not used before
        if (isTicketUsed(ticket)) return "";

        // Create verification code
        Token newToken = new Token(ticket, expirationDate);
        Token token = tokenRepository.save(newToken);

        return token.getId().toString();
    }

    @Override
    public Token findTokenByUseTicketCode(String verificationToken) {
        try {
            UUID id = UUID.fromString(verificationToken);
            return tokenRepository.findById(id).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Boolean validateUseTicketCode(Token token) throws Exception {
        if (token == null) return false;
        if (token.getVerifiedAt() != null) return false;

        Date currentDate = new Date();
        // Validate if token is not expired
        if (token.getExpiresAt().compareTo(currentDate) < 0)
            return false;

        // Validate if ticket was not used before
        Ticket ticket = token.getTicket();
        if (isTicketUsed(ticket)) return false;

        // Update verification code
        token.setVerifiedAt(currentDate);
        tokenRepository.save(token);
        return true;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public String startTransferTicket(User receiver) throws Exception {
        Date today = new Date();
        // Expiration date is set to 10 minutes
        Date reqExpiresAt = new Date(today.getTime() + (1000 * 60 * 10));

        Transaction newTransaction = new Transaction(receiver, reqExpiresAt);
        Transaction transaction = transactionRepository.save(newTransaction);

        return transaction.getId().toString();
    }

    @Override
    public Transaction findTransactionById(String transactionId) {
        try {
            UUID id = UUID.fromString(transactionId);
            return transactionRepository.findById(id).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void acceptTransferTicket(Transaction transaction, User sender, Ticket ticket) throws Exception {
        Date currentDate = new Date();
        // Expiration date is set to 10 minutes
        Date acceptExpiresAt = new Date(currentDate.getTime() + (1000 * 60 * 10));

        // Validate that reqExpiresAt hasn't happened
        if (transaction.getReqExpiresAt().compareTo(currentDate) < 0) return;
        // Validate if ticket was not used before
        if (isTicketUsed(ticket)) return;

        // Update accepted date and this step expiration date
        transaction.setAcceptAt(currentDate);
        transaction.setAcceptExpiresAt(acceptExpiresAt);

        // Update sender
        transaction.setFromUser(sender);
        // Update ticket that will be sent
        transaction.setTicket(ticket);

        transactionRepository.save(transaction);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void validateTransfer(Transaction transaction) throws Exception {
        if (transaction == null) return;
        if (transaction.getFinishedAt() != null) return;

        Date currentDate = new Date();
        // If ticket is not assigned to transaction is cause this operation is not valid
        Ticket ticket = transaction.getTicket();
        if (ticket == null) return;

        // Validate that acceptExpiresAt hasn't happened
        if (transaction.getAcceptExpiresAt().compareTo(currentDate) < 0) return;
        // Validate if ticket was not used before
        if (isTicketUsed(ticket)) return;

        // Update transaction finished date
        transaction.setFinishedAt(currentDate);
        transactionRepository.save(transaction);

        // Update ticket owner
        ticket.setUser(transaction.getToUser());
        ticketRepository.save(ticket);
    }

    @Override
    public int getEventCapacity(List<Tier> tiers) {
        return tiers.stream().mapToInt(Tier::getCapacity).sum();
    }

    @Override
    public int getEventTicketsSold(List<Tier> tiers) {
        return tiers.stream().mapToInt(tier -> tier.getTickets().size()).sum();
    }

    @Override
    public int getEventTicketsUsed(List<Tier> tiers) {
        List<Ticket> tickets = new ArrayList<>();
        tiers.forEach(t -> {
            tickets.addAll(t.getTickets());
        });

        final int[] ticketsUsed = {0};
        tickets.forEach(ticket -> {
            if (isTicketUsed(ticket))
                ticketsUsed[0] += 1;
        });

        return ticketsUsed[0];
    }

    @Override
    public int getTicketsSold() {
        return ticketRepository.findAll().size();
    }
}
