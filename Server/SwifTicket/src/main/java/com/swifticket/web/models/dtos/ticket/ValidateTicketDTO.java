package com.swifticket.web.models.dtos.ticket;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidateTicketDTO {
	@NotBlank(message = "verification token is required")
	private String verificationToken;
}
