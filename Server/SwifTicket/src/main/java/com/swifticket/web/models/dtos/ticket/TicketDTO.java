package com.swifticket.web.models.dtos.ticket;

import com.swifticket.web.models.dtos.event.EventDTO;
import com.swifticket.web.models.entities.Ticket;
import com.swifticket.web.models.entities.Tier;
import com.swifticket.web.models.entities.User;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
// DTO just to get Ticket data and not to set it
public class TicketDTO {
	private UUID id;
	private Tier tier;
	private Date createdAt;
	private EventDTO event;

	public TicketDTO(Ticket ticket, EventDTO event) {
		this.id = ticket.getId();
		this.tier = ticket.getTier();
		this.createdAt = ticket.getCreatedAt();
		this.event = event;
	}
}
