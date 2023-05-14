package com.swifticket.web.models.dtos.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUserDTO {
	@NotBlank(message = "name is required")
	private String name;
	
	@NotBlank(message = "avatar is required")
    private int avatarId;
}
