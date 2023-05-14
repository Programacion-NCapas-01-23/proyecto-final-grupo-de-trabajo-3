package com.swifticket.web.models.dtos.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketDTO {
	private String id;
	private String user;
	private String tier;
	private String createdAt;
}
