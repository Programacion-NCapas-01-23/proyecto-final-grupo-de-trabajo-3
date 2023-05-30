package com.swifticket.web.models.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserDTO {
	@NotBlank(message = "name is required")
	private String name;
	
	@NotBlank(message = "email is required")
	@Email(message = "email format is required")
	private String email;
	
	@NotBlank(message = "password is required")
	@Size(min = 5, message = "password must be at least 5 characters long")
	private String password;

	@Min(value = 1, message = "Invalid avatar key")
	private int avatarId;
}
