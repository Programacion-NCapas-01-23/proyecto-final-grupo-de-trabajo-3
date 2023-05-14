package com.swifticket.web.models.dtos.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemoveUserFromEventDTO {
	@NotBlank(message = "user id is required")
	private String userId;
	
	@NotBlank(message = "event id is required")
	private String eventId;
}