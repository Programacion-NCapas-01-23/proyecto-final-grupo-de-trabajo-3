package com.swifticket.web.models.dtos.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemoveUserFromEventDTO {
	@NotBlank(message = "user identifier is required")
	private String userId;
	
	@NotBlank(message = "event ID is required")
	private String eventId;
}
