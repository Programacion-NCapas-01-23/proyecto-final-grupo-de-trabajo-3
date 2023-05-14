package com.swifticket.web.models.dtos.sponsor;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveSponsorDTO {
	@NotBlank(message = "sponsor name is required")
	private String name;
	
	@NotBlank(message = "image is required")
	private String image;
}
