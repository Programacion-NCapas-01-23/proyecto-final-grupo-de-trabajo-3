package com.swifticket.web.models.dtos.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserDTO {
	@NotBlank(message = "name is required")
	@Size(min = 3,max = 25 ,message = "Format error: name must be at least 3 characters long")
	private String name;
	
	@NotBlank(message = "email is required")
	@Email(message = "Format error: email format is required")
	private String email;
	
	@NotBlank(message = "password is required")
	@Size(min = 8, message = "password size is 8 characters min")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_\\-+=<>?])[A-Za-z\\d!@#$%^&*()_\\-+=<>?]+$",
			message = "Format error: Password must contain 1 upper case, 1 lower case, 1 number, 1 special character")
	private String password;

	@Min(value = 1, message = "Invalid avatar key")
	private int avatarId;
}
