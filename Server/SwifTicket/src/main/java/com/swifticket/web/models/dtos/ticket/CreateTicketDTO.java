package com.swifticket.web.models.dtos.ticket;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTicketDTO {
	@NotBlank(message = "tier id is required")
	private String tierId;
}
