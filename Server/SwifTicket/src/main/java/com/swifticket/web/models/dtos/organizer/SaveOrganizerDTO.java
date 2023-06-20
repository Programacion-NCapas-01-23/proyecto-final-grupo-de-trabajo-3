package com.swifticket.web.models.dtos.organizer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveOrganizerDTO {
	@NotBlank(message = "organizer name is required")
	@Size(min = 3, max = 25, message = "Format error: The organizer name must be less than 25 characters and more than 3 characters")
	private String name;
}