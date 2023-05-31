package com.swifticket.web.models.dtos.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemoveRoleDTO {
	@NotBlank(message = "user id is required")
	private String userId;

	@Min(value = 1, message = "role is required")
	private int role;
}
