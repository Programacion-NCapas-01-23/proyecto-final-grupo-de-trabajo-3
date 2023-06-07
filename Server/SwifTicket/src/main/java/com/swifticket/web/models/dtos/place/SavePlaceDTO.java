package com.swifticket.web.models.dtos.place;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SavePlaceDTO {
	@NotBlank(message = "name is required")
	@Size(min = 3, max = 25, message = "Format error: The name of the place must be less than 25 characters and more than 3 characters")
	private String name;
	
	@NotBlank(message = "address is required")
	@Size(min = 10, max = 100, message = "Format error: Address must be between 10 and 100 characters")
	private String address;
}
