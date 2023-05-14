package com.swifticket.web.models.dtos.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssignRoleDTO {
	@NotBlank(message = "user id is required")
	private String userId;
	
	@NotBlank(message = "role is required")
	private int role;
}
