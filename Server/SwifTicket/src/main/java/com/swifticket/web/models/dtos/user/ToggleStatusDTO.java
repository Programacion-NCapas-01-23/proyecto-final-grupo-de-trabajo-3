package com.swifticket.web.models.dtos.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ToggleStatusDTO {
	@NotBlank(message = "user id is required")
	private String userId;

	/* TODO: check best approach
	@NotBlank(message = "user new status is required")
	private String state;
	 */

	@Min(value = 1, message = "state id is required")
	private int state;
}
