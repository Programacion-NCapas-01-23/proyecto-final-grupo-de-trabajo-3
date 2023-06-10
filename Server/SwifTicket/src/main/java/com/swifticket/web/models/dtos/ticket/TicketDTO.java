package com.swifticket.web.models.dtos.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
// DTO just to get Ticket data and not to set it
public class TicketDTO {
	private String id;
	private String user;
	private String tier;
	private String createdAt;
}
