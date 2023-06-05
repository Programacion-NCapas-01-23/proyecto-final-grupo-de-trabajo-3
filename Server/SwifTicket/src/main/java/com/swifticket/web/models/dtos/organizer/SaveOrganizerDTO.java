package com.swifticket.web.models.dtos.organizer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveOrganizerDTO {
	@NotBlank(message = "organizer name is required")
	@Size(max = 25, message = "The organizer name must be less than 25 characters")
	private String name;
}
