package com.swifticket.web.models.dtos.event;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemoveSponsorFromEventDTO {
	@NotBlank(message = "event id is required")
	private String eventId;
	
	@NotBlank(message = "sponsor id is required")
	private String sponsorId;
}
