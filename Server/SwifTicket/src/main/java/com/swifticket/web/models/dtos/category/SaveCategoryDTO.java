package com.swifticket.web.models.dtos.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveCategoryDTO {
	@NotBlank(message = "category name is required")
	@Size(max = 25, message = "the category name must be less than 25 characters")
	private String name;
}
