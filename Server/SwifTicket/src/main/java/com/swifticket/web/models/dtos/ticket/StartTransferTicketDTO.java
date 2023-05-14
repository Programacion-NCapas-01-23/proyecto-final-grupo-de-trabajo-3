package com.swifticket.web.models.dtos.ticket;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StartTransferTicketDTO {
	@NotBlank(message = "receiver is required")
	private String toId;
}
