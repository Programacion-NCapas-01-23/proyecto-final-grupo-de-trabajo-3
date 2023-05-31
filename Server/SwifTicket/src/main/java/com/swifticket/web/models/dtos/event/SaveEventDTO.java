package com.swifticket.web.models.dtos.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveEventDTO {
	@NotBlank(message = "title is required")
	private String title;
	
	@NotBlank(message = "duration is required")
	@Positive(message = "duration must be a positive number")
	private String duration;
	
	@NotBlank(message = "date and time is required")
	private String dateTime;
	
	@NotBlank(message = "image is required")
	private String image;
	
	@NotBlank(message = "place id is required")
	private int placeId;
	
	@NotBlank(message = "category id is required")
	private int categoryId;
	
	@NotBlank(message = "organizer id is required")
	private int organizerId;
}
