package com.swifticket.web.models.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordDTO {
	@NotBlank(message = "password is required")
	private String password;

	@NotBlank(message = "a new password is required")
	@Size(min = 8, message = "password size is 8 characters min")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_\\-+=<>?])[A-Za-z\\d!@#$%^&*()_\\-+=<>?]+$",
			message = "Format error: Password must contain 1 upper case, 1 lower case, 1 number, 1 special character")
	private String newPassword;
}
