package com.swifticket.web.controllers;

import com.swifticket.web.models.dtos.response.MessageDTO;
import com.swifticket.web.models.dtos.stats.EventAttendanceStatsDTO;
import com.swifticket.web.models.dtos.stats.EventStatsDTO;
import com.swifticket.web.models.dtos.stats.GeneralStatsDTO;
import com.swifticket.web.models.entities.Event;
import com.swifticket.web.models.entities.Tier;
import com.swifticket.web.models.entities.User;
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

@RestController
@RequestMapping("/stats")
@CrossOrigin("*")
// TODO: implement methods to provide charts data
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
		List<User> users = userServices.findAll();
		List<Event> events = eventServices.findAll();

		int ticketsSold = ticketServices.getTicketsSold();
		int attendance = ticketServices.getTicketsUsed();
		// Get percentage of users that attended single vs in group
		double attendanceSingle = ticketServices.getAttendanceSingle() / attendance * 100;
		double attendanceGroup = 100 - attendanceSingle;

		GeneralStatsDTO response = new GeneralStatsDTO(
				users.size(), events.size(), ticketsSold, attendance, attendanceSingle, attendanceGroup
		);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/event/{id}")
	public ResponseEntity<?> getEventStats(@PathVariable String id) {
		Event event = eventServices.findById(id);
		if (event == null)
			return new ResponseEntity<>(new MessageDTO("event not found"), HttpStatus.NOT_FOUND);

		List<Tier> tiers = event.getTiers();
		// Get event total capacity
		int capacity = ticketServices.getEventCapacity(tiers);
		// Get tickets sold by event
		int tickets = ticketServices.getEventTicketsSold(tiers);
		// Ratio of sold tickets
		double ratio = (double) tickets / capacity * 100;
		// Get tickets that have been validated
		int attendants = ticketServices.getEventTicketsUsed(tiers);
		double attendantsVsTicketsSold = (double) attendants / tickets * 100;
		// Get percentage of users that attended single vs in group
		double attendanceSingle = ticketServices.getEventAttendanceSingle(tiers) / attendants * 100;
		double attendanceGroup = 100 - attendanceSingle;
		List<Double> attendance = new ArrayList<>();
		attendance.add(attendanceSingle);
		attendance.add(attendanceGroup);

		// Get array of tickets sold by tier
		List<String> tiersNames = tiers.stream().map(Tier::getName).toList();
		List<Integer> ticketsSoldByTier = tiers.stream().map(t -> {
			return t.getTickets().size();
		}).toList();

		EventStatsDTO response = new EventStatsDTO(
				capacity,
				tickets,
				ratio,
				attendants,
				attendantsVsTicketsSold,
				attendance,
				tiersNames,
				ticketsSoldByTier
		);
		return new ResponseEntity<>(response, HttpStatus.OK);
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
		int tickets = ticketServices.getEventTicketsSold(tiers);
		// Ratio of sold tickets
		double ratio = (double) tickets / capacity * 100;
		// Get tickets that have been validated
		int attendants = ticketServices.getEventTicketsUsed(tiers);
		double attendantsVsTicketsSold = (double) attendants / tickets * 100;

		EventAttendanceStatsDTO response = new EventAttendanceStatsDTO(
				capacity, tickets, ratio,attendants, attendantsVsTicketsSold
		);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
