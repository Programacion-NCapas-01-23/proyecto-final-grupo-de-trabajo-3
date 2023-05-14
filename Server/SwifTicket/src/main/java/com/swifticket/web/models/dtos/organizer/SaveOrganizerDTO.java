package com.swifticket.web.models.dtos.organizer;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveOrganizerDTO {
	@NotBlank(message = "organizer name is required")
	private String name;
}
