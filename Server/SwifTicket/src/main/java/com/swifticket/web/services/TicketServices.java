package com.swifticket.web.services;

import java.util.List;

import com.swifticket.web.models.entities.Ticket;
import com.swifticket.web.models.entities.Tier;
import com.swifticket.web.models.entities.User;

public interface TicketServices {
	Ticket findOneById(String ticketId);
	List<Ticket> findAllByUser(User user);
	void create(User user, Tier tier) throws Exception;
	
	String generateCode(Ticket ticket);
	Boolean validateTicket(String verificationToken);
	
	String startTransferTicket(User receiver) throws Exception;
	void acceptTransferTicket(String transferId, User sender, Ticket ticket) throws Exception;
	void validateTransfer(String code) throws Exception;
}
