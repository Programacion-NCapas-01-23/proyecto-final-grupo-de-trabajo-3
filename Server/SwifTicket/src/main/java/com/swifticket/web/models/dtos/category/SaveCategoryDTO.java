package com.swifticket.web.models.dtos.category;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveCategoryDTO {
	@NotBlank(message = "category name is required")
	private String name;
}
