package com.swifticket.web.models.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
