package com.swifticket.web.services;

import java.util.List;

import com.swifticket.web.models.entities.*;

public interface TicketServices {
	Ticket findOneById(String ticketId);
	List<Ticket> findAllByUser(User user);
	void create(User user, Tier tier) throws Exception;

	Boolean isTicketUsed(Ticket ticket);
	String generateUseTicketCode(Ticket ticket) throws Exception;
	Token findTokenByUseTicketCode(String verificationToken);
	Boolean validateUseTicketCode(Token token) throws  Exception;
	
	String startTransferTicket(User receiver) throws Exception;
	Transaction findTransactionById(String transactionId);
	void acceptTransferTicket(Transaction transaction, User sender, Ticket ticket) throws Exception;
	void validateTransfer(Transaction transaction) throws Exception;

	int getEventCapacity(List<Tier> tiers);
	int getTicketsSold(List<Tier> tiers);
}
