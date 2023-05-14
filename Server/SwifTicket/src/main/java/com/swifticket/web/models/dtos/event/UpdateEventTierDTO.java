package com.swifticket.web.models.dtos.event;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateEventTierDTO {
	@NotBlank(message = "name is required")
	private String name;
	
	@NotBlank(message = "capacity is required")
	private int capacity;
	
	@NotBlank(message = "price is required")
	private Double price;
}
