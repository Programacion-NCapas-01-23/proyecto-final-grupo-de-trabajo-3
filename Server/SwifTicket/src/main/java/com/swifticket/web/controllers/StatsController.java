package com.swifticket.web.controllers;

import com.swifticket.web.models.dtos.response.MessageDTO;
import com.swifticket.web.models.dtos.stats.EventAttendanceStatsDTO;
import com.swifticket.web.models.entities.Event;
import com.swifticket.web.models.entities.Ticket;
import com.swifticket.web.models.entities.Tier;
import com.swifticket.web.services.EventServices;
import com.swifticket.web.services.TicketServices;
import com.swifticket.web.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stats")
@CrossOrigin("*")
// TODO: implement this controller
public class StatsController {
	private final TicketServices ticketServices;
	private final UserServices userServices;
	private final EventServices eventServices;

	@Autowired
	public StatsController(TicketServices ticketServices, UserServices userServices, EventServices eventServices) {
		this.ticketServices = ticketServices;
		this.userServices = userServices;
		this.eventServices = eventServices;
	}

	@GetMapping("/general")
	public ResponseEntity<?> getGeneralStats() {

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/event/{id}")
	public ResponseEntity<?> getEventStats(@PathVariable String id) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/event/{id}/attendance")
	public ResponseEntity<?> getEventAttendanceStats(@PathVariable String id) {
		Event event = eventServices.findById(id);
		if (event == null)
			return new ResponseEntity<>(new MessageDTO("event not found"), HttpStatus.NOT_FOUND);

		List<Tier> tiers = event.getTiers();
		// Get event total capacity
		int capacity = ticketServices.getEventCapacity(tiers);

		// Get tickets sold by event
		int tickets = ticketServices.getTicketsSold(tiers);

		// Ratio of sold tickets
		double ratio = (double) tickets / capacity * 100;

		EventAttendanceStatsDTO response = new EventAttendanceStatsDTO(capacity, tickets, ratio);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
