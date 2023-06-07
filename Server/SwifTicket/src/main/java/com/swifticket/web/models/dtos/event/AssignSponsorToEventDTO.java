package com.swifticket.web.models.dtos.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssignSponsorToEventDTO {
	@NotBlank(message = "event id is required")
	private String eventId;
	
	@NotBlank(message = "sponsor name is required")
	@Size(min = 3, max = 25, message = "Format error: The sponsor name must be less than 25 characters and more than 3 characters")
	private String sponsorName;
}
