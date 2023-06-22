package com.swifticket.web.models.dtos.sponsor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class SaveSponsorDTO {
	@NotBlank(message = "sponsor name is required")
	@Size(max = 25, message = "Format error: The sponsor name must be less than 25 characters")
	private String name;

	@NotNull(message = "image is required")
	private MultipartFile image;
}

