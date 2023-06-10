package com.swifticket.web.models.dtos.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
// DTO just to get THE EVENT data and not to set it
public class EventDTO {
	private String title;
	private String duration;
	private String dateTime;
	private String image;
	private String place;
	private String category;
	private String organizer;
}
