package com.swifticket.web.models.dtos.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUserDTO {
	@NotBlank(message = "name is required")
	@Size(min = 3,max = 25 ,message = "Format error: name must be at least 3 characters long")
	private String name;

	@Min(value = 1, message = "Invalid avatar key")
	private int avatar;
}
