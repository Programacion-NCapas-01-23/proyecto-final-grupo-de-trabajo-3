package com.swifticket.web.models.dtos.event;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteTierFromEventDTO {
	@NotBlank(message = "event id is required")
	private String eventId;
	
	@NotBlank(message = "tier id is required")
	private String tierId;
}