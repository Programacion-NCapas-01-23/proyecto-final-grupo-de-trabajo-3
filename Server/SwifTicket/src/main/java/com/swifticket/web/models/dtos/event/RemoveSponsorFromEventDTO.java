package com.swifticket.web.models.dtos.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemoveSponsorFromEventDTO {
	@NotBlank(message = "event id is required")
	private String eventId;
	
	@Positive(message = "sponsor id is required")
	private int sponsorId;
}
