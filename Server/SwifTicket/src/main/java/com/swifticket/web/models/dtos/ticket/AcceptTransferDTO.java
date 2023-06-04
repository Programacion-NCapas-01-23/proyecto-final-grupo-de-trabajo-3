package com.swifticket.web.models.dtos.ticket;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AcceptTransferDTO {
	@NotBlank(message = "transfer id is required")
	private String transferId;
	
	@NotBlank(message = "ticket id is required")
	private String ticketId;
}
