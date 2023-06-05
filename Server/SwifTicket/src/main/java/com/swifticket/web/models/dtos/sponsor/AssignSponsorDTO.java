package com.swifticket.web.models.dtos.sponsor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssignSponsorDTO {
	@NotBlank(message = "event id is required")
	private String eventId;

	@NotBlank(message = "sponsor name is required")
	@Size(max = 25, message = "The sponsor name must be less than 25 characters")
	private String name;
	
	@NotBlank(message = "image is required")
	private String image;
}
