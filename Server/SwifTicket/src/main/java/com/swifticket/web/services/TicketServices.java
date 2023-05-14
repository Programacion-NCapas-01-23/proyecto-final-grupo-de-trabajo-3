package com.swifticket.web.services;

import java.util.List;

import com.swifticket.web.models.entities.Ticket;

public interface TicketServices {
	Ticket findOneById(String ticketId);
	List<Ticket> findAllByUser(String userId);
	void create(String userId, String tierId);
	
	String generateCode(String ticketId);
	Boolean validateTicket(String verificationToken);
	
	String startTransferTicket(String receiver);
	void acceptTransferTicket(String transferId, String sender, String ticketId);
	void validateTransfer(String code);
}
