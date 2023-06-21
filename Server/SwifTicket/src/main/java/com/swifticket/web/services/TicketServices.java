package com.swifticket.web.services;

import java.util.Date;
import java.util.List;

import com.swifticket.web.models.entities.*;
import org.springframework.data.domain.Page;

public interface TicketServices {
	Ticket findOneById(String ticketId);
	Page<Ticket> findAllByUser(User user, int page, int size);
	void create(User user, Tier tier) throws Exception;

	Boolean isTicketUsed(Ticket ticket);
	Date getTicketValidationDate(Ticket ticket);
	String generateUseTicketCode(Ticket ticket) throws Exception;
	Token findTokenByUseTicketCode(String verificationToken);
	Boolean validateUseTicketCode(Token token) throws  Exception;
	
	String startTransferTicket(User receiver) throws Exception;
	Transaction findTransactionById(String transactionId);
	String acceptTransferTicket(Transaction transaction, User sender, Ticket ticket) throws Exception;
	void validateTransfer(Transaction transaction) throws Exception;

	int getEventCapacity(List<Tier> tiers);
	int getEventTicketsSold(List<Tier> tiers);
	List<Ticket> getEventTicketsUsed(List<Tier> tiers);
	double getEventAttendanceSingle(List<Tier> tiers);
	int getTicketsSold();
	int getTicketsUsed();
	double getAttendanceSingle();
}
