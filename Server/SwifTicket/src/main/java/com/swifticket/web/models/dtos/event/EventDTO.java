package com.swifticket.web.models.dtos.event;

import com.swifticket.web.models.entities.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
// DTO just to get THE EVENT data and not to set it
public class EventDTO {
	private UUID id;
	private Category category;
	private Organizer organizer;
	private EventState state;
	private String title;
	private double duration;
	private Date dateTime;
	private String image;
	private Place place;
	private boolean isAvailable;

	public EventDTO(Event event, boolean isAvailable) {
		this.id = event.getId();
		this.category = event.getCategory();
		this.organizer = event.getOrganizer();
		this.state = event.getState();
		this.title = event.getTitle();
		this.duration = event.getDuration();
		this.dateTime = event.getDateTime();
		this.image = event.getImage();
		this.place = event.getPlace();
		this.isAvailable = isAvailable;
	}
}
