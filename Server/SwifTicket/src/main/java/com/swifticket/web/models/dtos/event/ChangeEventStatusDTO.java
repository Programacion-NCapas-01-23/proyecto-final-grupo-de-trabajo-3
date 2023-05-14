package com.swifticket.web.models.dtos.event;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeEventStatusDTO {
	@NotBlank(message = "event id is required")
	private String id;
	
	@NotBlank(message = "event new status is required")
	private String status;
}
