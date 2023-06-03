package com.swifticket.web.controllers;

import com.swifticket.web.models.dtos.response.CodeDTO;
import com.swifticket.web.models.dtos.response.MessageDTO;
import com.swifticket.web.models.dtos.ticket.CreateTicketDTO;
import com.swifticket.web.models.dtos.ticket.GenerateTicketCodeDTO;
import com.swifticket.web.models.dtos.ticket.ValidateTicketDTO;
import com.swifticket.web.models.entities.Ticket;
import com.swifticket.web.models.entities.Tier;
import com.swifticket.web.models.entities.Token;
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

import java.util.Date;
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
	public ResponseEntity<?> generateValidateTicketCode(
			@ModelAttribute @Valid GenerateTicketCodeDTO data, BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		Ticket ticket = ticketServices.findOneById(data.getTicketId());
		if (ticket == null)
			return new ResponseEntity<>(new MessageDTO("ticket not found"), HttpStatus.NOT_FOUND);

		try {
			String code = ticketServices.generateUseTicketCode(ticket);
			if (code.isEmpty())
				return new ResponseEntity<>(new MessageDTO("ticket already used"), HttpStatus.CONFLICT);

			return new ResponseEntity<>(new CodeDTO(code), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PatchMapping("/validate-ticket")
	public ResponseEntity<?> validateTicket(
			@ModelAttribute @Valid ValidateTicketDTO data, BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		Token token = ticketServices.findTokenByUseTicketCode(data.getVerificationToken());
		if (token == null)
			return new ResponseEntity<>(new MessageDTO("invalid verification code"), HttpStatus.NOT_FOUND);

		if (token.getVerifiedAt() != null)
			return new ResponseEntity<>(new MessageDTO("ticket has already been validated"), HttpStatus.CONFLICT);

		Date currentDate = new Date();
		// Validate if token is not expired
		if (token.getExpiresAt().compareTo(currentDate) < 0)
			return new ResponseEntity<>(new MessageDTO("verification code is expired"), HttpStatus.CONFLICT);

		// Validate if ticket was not used before
		Ticket ticket = token.getTicket();
		List<Token> tokens = ticket.getTokens();
		final Boolean[] wasUsed = {false};
		tokens.forEach(t -> {
			if (t.getVerifiedAt() != null)
				wasUsed[0] = true;
		});

		if (wasUsed[0])
			return new ResponseEntity<>(new MessageDTO("ticket has already been validated"), HttpStatus.CONFLICT);

		try {
			Boolean success = ticketServices.validateUseTicketCode(token);
			if (!success)
				return new ResponseEntity<>(new MessageDTO("invalid verification code"), HttpStatus.CONFLICT);

			return new ResponseEntity<>(new MessageDTO("token used"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
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
