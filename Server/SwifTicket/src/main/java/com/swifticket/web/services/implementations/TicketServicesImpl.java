package com.swifticket.web.services.implementations;

import com.swifticket.web.models.entities.*;
import com.swifticket.web.repositories.BillRepository;
import com.swifticket.web.repositories.TicketRepository;
import com.swifticket.web.repositories.TokenRepository;
import com.swifticket.web.repositories.TransactionRepository;
import com.swifticket.web.services.TicketServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class TicketServicesImpl implements TicketServices {

    private final TicketRepository ticketRepository;
    private final TokenRepository tokenRepository;
    private final TransactionRepository transactionRepository;
    private final BillRepository billRepository;

    @Autowired
    public TicketServicesImpl(TicketRepository ticketRepository, TokenRepository tokenRepository, TransactionRepository transactionRepository, BillRepository billRepository) {
        this.ticketRepository = ticketRepository;
        this.tokenRepository = tokenRepository;
        this.transactionRepository = transactionRepository;
        this.billRepository = billRepository;
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
    public Page<Ticket> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ticketRepository.findAll(pageable);
    }

    @Override
    public List<Ticket> findAllByUser(User user) {
        return ticketRepository.findByUser(user);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void create(User user, Tier tier) throws Exception {
        Ticket newTicket = new Ticket(user, tier);
        Ticket ticket = ticketRepository.save(newTicket);

        // Save ticket bill for audit
        Bill bill = new Bill(ticket.getId(), user.getId(), tier.getPrice());
        billRepository.save(bill);
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
    public Date getTicketValidationDate(Ticket ticket) {
        // Find when the ticket was used
        List<Token> tokens = ticket.getTokens();
        final Date[] usedIn = new Date[1];
        tokens.forEach(t -> {
            if (t.getVerifiedAt() != null)
                usedIn[0] = t.getVerifiedAt();
        });

        return usedIn[0];
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
    public List<Ticket> getEventTicketsUsed(List<Tier> tiers) {
        List<Ticket> tickets = new ArrayList<>();
        tiers.forEach(t -> {
            tickets.addAll(t.getTickets());
        });

        // Filter tickets to work only with used tickets
        return tickets.stream().filter(this::isTicketUsed).toList();
    }

    @Override
    public double getEventAttendanceSingle(List<Tier> tiers) {
        int singleAttendance = 0;
        Map<UUID, Integer> users = new HashMap<>();

        // Get all the sold tickets for this event
        List<Ticket> tickets = new ArrayList<>();
        tiers.forEach(t -> {
            tickets.addAll(t.getTickets());
        });
        // Filter tickets to work only with used tickets
        List<Ticket> usedTickets = tickets.stream().filter(this::isTicketUsed).toList();

        // Build a map with the user's id and the number of tickets
        // that were bought for this event
        usedTickets.forEach(ticket -> {
            UUID userId = ticket.getUser().getId();
            if (users.containsKey(userId))
                users.put(userId, users.get(userId) + 1);
            else
                users.put(userId, 1);
        });

        // From user map get users that buy only one ticket for this event
        for (Map.Entry<UUID, Integer> entry : users.entrySet()) {
            Integer value = entry.getValue();
            if (value == 1) singleAttendance++;
        }

        return singleAttendance;
    }

    @Override
    public int getTicketsSold() {
        return ticketRepository.findAll().size();
    }

    @Override
    public int getTicketsUsed() {
        // Get all the sold tickets
        List<Ticket> tickets = ticketRepository.findAll();
        // Filter tickets to work only with used tickets
        List<Ticket> usedTickets = tickets.stream().filter(this::isTicketUsed).toList();

        return usedTickets.size();
    }

    @Override
    public double getAttendanceSingle() {
        int singleAttendance = 0;
        Map<String, Integer> users = new HashMap<>();

        // Get all the sold tickets
        List<Ticket> tickets = ticketRepository.findAll();
        // Filter tickets to work only with used tickets
        List<Ticket> usedTickets = tickets.stream().filter(this::isTicketUsed).toList();

        // Build a map with the user's id and the number of tickets
        // that were bought using a user's id + event's id key
        usedTickets.forEach(ticket -> {
            String key = ticket.getUser().getId().toString()
                    + ticket.getTier().getEvent().getId().toString();
            if (users.containsKey(key))
                users.put(key, users.get(key) + 1);
            else
                users.put(key, 1);
        });

        // From user map get users that buy only one ticket for this event
        for (Map.Entry<String, Integer> entry : users.entrySet()) {
            Integer value = entry.getValue();
            if (value == 1) singleAttendance++;
        }

        return singleAttendance;
    }
}
