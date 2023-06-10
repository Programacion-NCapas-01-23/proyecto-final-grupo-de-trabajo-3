package com.swifticket.web.models.dtos.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemoveRoleDTO {
	@NotBlank(message = "user identifier is required")
	private String userId;

	@Min(value = 1, message = "role is required, it can be 1 for admin,2 for user, 3 for moderator and 4 for collaborator")
	private int role;
}
