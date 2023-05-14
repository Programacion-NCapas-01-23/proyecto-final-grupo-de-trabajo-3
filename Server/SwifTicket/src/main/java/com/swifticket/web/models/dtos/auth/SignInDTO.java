package com.swifticket.web.models.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInDTO {
	@NotBlank(message = "email is required")
	private String email;
	
	@NotBlank(message = "password is required")
	private String password;
}
