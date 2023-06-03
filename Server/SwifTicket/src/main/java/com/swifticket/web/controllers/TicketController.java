package com.swifticket.web.controllers;

import com.swifticket.web.models.dtos.response.MessageDTO;
import com.swifticket.web.models.dtos.ticket.CreateTicketDTO;
import com.swifticket.web.models.entities.Ticket;
import com.swifticket.web.models.entities.Tier;
import com.swifticket.web.models.entities.User;
import com.swifticket.web.services.TicketServices;
import com.swifticket.web.services.TierServices;
import com.swifticket.web.services.UserServices;
import com.swifticket.web.utils.ErrorHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@CrossOrigin("*")
public class TicketController {

	@Autowired
	private TicketServices ticketServices;
	@Autowired
	private TierServices tierServices;
	@Autowired
	private UserServices userServices;
	@Autowired
	private ErrorHandler errorHandler;

	@GetMapping("/{id}")
	public ResponseEntity<?> getTicket(@PathVariable String id) {
		Ticket ticket = ticketServices.findOneById(id);
		if (ticket == null)
			return new ResponseEntity<>(new MessageDTO("ticket not found"), HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(ticket, HttpStatus.OK);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getTicketsByUser(@PathVariable String id) {
		User user = userServices.findOneByEmail(id);
		if (user == null)
			return new ResponseEntity<>(new MessageDTO("user not found"), HttpStatus.NOT_FOUND);

		List<Ticket> tickets = user.getTickets();
		return new ResponseEntity<>(tickets, HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<?> createTicket(
			@ModelAttribute @Valid CreateTicketDTO data, BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		Tier tier = tierServices.findById(data.getTierId());
		if (tier == null)
			return new ResponseEntity<>(new MessageDTO("tier not found"), HttpStatus.NOT_FOUND);

		User user = userServices.findOneByEmail(data.getUserId());
		if (user == null)
			return new ResponseEntity<>(new MessageDTO("user not found"), HttpStatus.NOT_FOUND);

		// Validate capacity availability
		if (tier.getTickets().size() >= tier.getCapacity())
			return new ResponseEntity<>(new MessageDTO("event tier is sold out"), HttpStatus.CONFLICT);

		try {
			ticketServices.create(user, tier);
			return new ResponseEntity<>(new MessageDTO("ticket created"), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/generate-code")
	public ResponseEntity<?> generateValidateTicketCode() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PatchMapping("/validate-ticket")
	public ResponseEntity<?> validateTicket() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/transfer")
	public ResponseEntity<?> startTransferTicket() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/transfer/{id}")
	public ResponseEntity<?> acceptTransferTicket(@PathVariable String id) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/transfer/validate-transfer/{code}")
	public ResponseEntity<?> finishTransferTicket(@PathVariable String code) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
