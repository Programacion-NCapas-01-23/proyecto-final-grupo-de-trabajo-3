package com.swifticket.web.models.dtos.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
	@Pattern(regexp = "^(0[1-9]|1[0-9]|2[0-9]|3[01])/(0[1-9]|1[012])/\\d{4}$", message = "Format error: Date format must be exactly DD/MM/YYYY")
	private String dateTime;

	private String src;
	
	@Positive(message = "place id is required")
	private int placeId;
	
	@Positive(message = "category id is required")
	private int categoryId;
	
	@Positive(message = "organizer id is required")
	private int organizerId;
}
