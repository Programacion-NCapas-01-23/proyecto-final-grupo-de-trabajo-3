package com.swifticket.web.models.dtos.place;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SavePlaceDTO {
	@NotBlank(message = "name is required")
	private String name;
	
	@NotBlank(message = "address is required")
	private String address;
}
