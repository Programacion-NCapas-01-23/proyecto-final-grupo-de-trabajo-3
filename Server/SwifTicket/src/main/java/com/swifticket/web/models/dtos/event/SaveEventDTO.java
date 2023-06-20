package com.swifticket.web.models.dtos.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class SaveEventDTO {
	@NotBlank(message = "title is required")
	private String title;
	
	@NotBlank(message = "duration is required")
	@Positive(message = "Value error: duration must be a positive number")
	private String duration;
	
	@NotBlank(message = "date and time is required")
	private String dateTime;
	
	@NotBlank(message = "image is required")
	private MultipartFile image;
	
	@Positive(message = "place id is required")
	private int placeId;
	
	@Positive(message = "category id is required")
	private int categoryId;
	
	@Positive(message = "organizer id is required")
	private int organizerId;
}