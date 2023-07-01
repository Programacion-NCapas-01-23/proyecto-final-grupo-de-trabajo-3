package com.swifticket.web.controllers;

import com.swifticket.web.models.dtos.event.EventDTO;
import com.swifticket.web.models.dtos.page.PageDTO;
import com.swifticket.web.models.dtos.response.CodeDTO;
import com.swifticket.web.models.dtos.response.MessageDTO;
import com.swifticket.web.models.dtos.ticket.*;
import com.swifticket.web.models.dtos.tier.TierDTO;
import com.swifticket.web.models.entities.*;
import com.swifticket.web.services.EmailServices;
import com.swifticket.web.services.TicketServices;
import com.swifticket.web.services.TierServices;
import com.swifticket.web.services.UserServices;
import com.swifticket.web.utils.ErrorHandler;
import com.swifticket.web.utils.RoleCatalog;
import com.swifticket.web.utils.RoleVerifier;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

	private final TicketServices ticketServices;
	private final TierServices tierServices;
	private final UserServices userServices;
	private final ErrorHandler errorHandler;
	private final EmailServices emailServices;
	@Autowired
	public TicketController(TicketServices ticketServices, TierServices tierServices, UserServices userServices, ErrorHandler errorHandler, EmailServices emailServices) {
		this.ticketServices = ticketServices;
		this.tierServices = tierServices;
		this.userServices = userServices;
		this.errorHandler = errorHandler;
		this.emailServices = emailServices;
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getTicket(@PathVariable String id) {
		User authUser = userServices.findUserAuthenticated();
		// Grant access by user's role -> USER (owner), SUPER_ADMIN
		int[] validRoles = {RoleCatalog.USER, RoleCatalog.SUPER_ADMIN};
		if (!RoleVerifier.userMatchesRoles(validRoles, userServices.getUserRoles(authUser)))
			return new ResponseEntity<>(new MessageDTO("Credential permissions not valid"), HttpStatus.UNAUTHORIZED);

		Ticket ticket = ticketServices.findOneById(id);
		if (ticket == null)
			return new ResponseEntity<>(new MessageDTO("ticket not found"), HttpStatus.NOT_FOUND);

		// Validate ticket ownership
		if (ticket.getUser().getId() != authUser.getId())
			return new ResponseEntity<>(new MessageDTO("current user is not the owner of this ticket"), HttpStatus.UNAUTHORIZED);

		// Return formatted ticket
		Event event = ticket.getTier().getEvent();
		EventDTO _event = new EventDTO(event, true);
		TicketDTO _ticket = new TicketDTO(ticket, ticketServices.isTicketUsed(ticket), _event);

		return new ResponseEntity<>(_ticket, HttpStatus.OK);
	}

	@GetMapping("/user")
	public ResponseEntity<?> getTicketsByUser(@RequestParam(defaultValue = "0") int page,
											  @RequestParam(defaultValue = "10") int size) {
		User authUser = userServices.findUserAuthenticated();
		// Grant access by user's role -> USER (owner), SUPER_ADMIN
		int[] validRoles = {RoleCatalog.USER, RoleCatalog.SUPER_ADMIN};
		if (!RoleVerifier.userMatchesRoles(validRoles, userServices.getUserRoles(authUser)))
			return new ResponseEntity<>(new MessageDTO("Credential permissions not valid"), HttpStatus.UNAUTHORIZED);

		Page<Ticket> tickets = ticketServices.findAllByUser(authUser, page, size);

		List<TicketDTO> _tickets = tickets.getContent().stream().map(ticket -> {
			Event event = ticket.getTier().getEvent();
			EventDTO _event = new EventDTO(event, true);
			return new TicketDTO(ticket, ticketServices.isTicketUsed(ticket), _event);
		}).toList();

		PageDTO<TicketDTO> response = new PageDTO<>(
				_tickets,
				tickets.getNumber(),
				tickets.getSize(),
				tickets.getTotalElements(),
				tickets.getTotalPages(),
				tickets.isEmpty()
		);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<?> createTicket(
			@ModelAttribute @Valid CreateTicketDTO data, BindingResult validations) {
		User authUser = userServices.findUserAuthenticated();
		// Grant access by user's role -> USER
		int[] validRoles = {RoleCatalog.USER, RoleCatalog.SUPER_ADMIN};
		if (!RoleVerifier.userMatchesRoles(validRoles, userServices.getUserRoles(authUser)))
			return new ResponseEntity<>(new MessageDTO("Credential permissions not valid"), HttpStatus.UNAUTHORIZED);

		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		Tier tier = tierServices.findById(data.getTierId());
		if (tier == null)
			return new ResponseEntity<>(new MessageDTO("tier not found"), HttpStatus.NOT_FOUND);

		// Validate capacity availability
		if (tier.getTickets().size() >= tier.getCapacity())
			return new ResponseEntity<>(new MessageDTO("event tier is sold out"), HttpStatus.CONFLICT);

		try {
			ticketServices.create(authUser, tier);
			return new ResponseEntity<>(new MessageDTO("ticket created"), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/generate-code")
	public ResponseEntity<?> generateValidateTicketCode(
			@ModelAttribute @Valid GenerateTicketCodeDTO data, BindingResult validations) {
		User authUser = userServices.findUserAuthenticated();
		// Grant access by user's role -> USER
		int[] validRoles = {RoleCatalog.USER, RoleCatalog.SUPER_ADMIN};
		if (!RoleVerifier.userMatchesRoles(validRoles, userServices.getUserRoles(authUser)))
			return new ResponseEntity<>(new MessageDTO("Credential permissions not valid"), HttpStatus.UNAUTHORIZED);

		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		Ticket ticket = ticketServices.findOneById(data.getTicketId());
		if (ticket == null)
			return new ResponseEntity<>(new MessageDTO("ticket not found"), HttpStatus.NOT_FOUND);

		// Validate ticket ownership
		if (ticket.getUser().getId() != authUser.getId())
			return new ResponseEntity<>(new MessageDTO("current user is not the owner of this ticket"), HttpStatus.UNAUTHORIZED);

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
		User authUser = userServices.findUserAuthenticated();
		// Grant access by user's role -> COLLABORATOR
		int[] validRoles = {RoleCatalog.COLLABORATOR, RoleCatalog.SUPER_ADMIN	};
		if (!RoleVerifier.userMatchesRoles(validRoles, userServices.getUserRoles(authUser)))
			return new ResponseEntity<>(new MessageDTO("Credential permissions not valid"), HttpStatus.UNAUTHORIZED);

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
		if (ticketServices.isTicketUsed(ticket))
			return new ResponseEntity<>(new MessageDTO("ticket has already been validated"), HttpStatus.CONFLICT);

		try {
			Boolean success = ticketServices.validateUseTicketCode(token);
			if (!success)
				return new ResponseEntity<>(new MessageDTO("invalid verification code"), HttpStatus.CONFLICT);

			return new ResponseEntity<>(new MessageDTO("Ticket successfully validated"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/transfer")
	public ResponseEntity<?> startTransferTicket() {
		User authUser = userServices.findUserAuthenticated();
		// Grant access by user's role -> USER
		int[] validRoles = {RoleCatalog.USER, RoleCatalog.SUPER_ADMIN};
		if (!RoleVerifier.userMatchesRoles(validRoles, userServices.getUserRoles(authUser)))
			return new ResponseEntity<>(new MessageDTO("Credential permissions not valid"), HttpStatus.UNAUTHORIZED);

		try {
			String code = ticketServices.startTransferTicket(authUser);
			return new ResponseEntity<>(new CodeDTO(code), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/transfer")
	public ResponseEntity<?> acceptTransferTicket(
			@ModelAttribute @Valid AcceptTransferDTO data, BindingResult validations) {
		User authUser = userServices.findUserAuthenticated();
		// Grant access by user's role -> USER
		int[] validRoles = {RoleCatalog.USER, RoleCatalog.SUPER_ADMIN};
		if (!RoleVerifier.userMatchesRoles(validRoles, userServices.getUserRoles(authUser)))
			return new ResponseEntity<>(new MessageDTO("Credential permissions not valid"), HttpStatus.UNAUTHORIZED);
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		Transaction transaction = ticketServices.findTransactionById(data.getTransferId());
		if (transaction == null)
			return new ResponseEntity<>(new MessageDTO("transaction not found"), HttpStatus.NOT_FOUND);
		if (transaction.getAcceptAt() != null)
			return new ResponseEntity<>(new MessageDTO("transaction already accepted"), HttpStatus.OK);

		// Validate that reqExpiresAt hasn't happened
		Date currentDate = new Date();
		if (transaction.getReqExpiresAt().compareTo(currentDate) < 0)
			return new ResponseEntity<>(new MessageDTO("transaction code is expired"), HttpStatus.CONFLICT);

		Ticket ticket = ticketServices.findOneById(data.getTicketId());
		if (ticket == null)
			return new ResponseEntity<>(new MessageDTO("ticket not found"), HttpStatus.NOT_FOUND);
		if (ticketServices.isTicketUsed(ticket))
			return new ResponseEntity<>(new MessageDTO("ticket has already been used"), HttpStatus.CONFLICT);

		User userFrom = ticket.getUser();
		if (userFrom == null)
			return new ResponseEntity<>(new MessageDTO("user not found"), HttpStatus.NOT_FOUND);

		// Validate ticket ownership
		if (ticket.getUser().getId() != authUser.getId())
			return new ResponseEntity<>(new MessageDTO("current user is not the owner of this ticket"), HttpStatus.UNAUTHORIZED);

		try {
			String code = ticketServices.acceptTransferTicket(transaction, userFrom, ticket);
			// Notify sender user to confirm transaction
			emailServices.sendVerificationTransactionCode(userFrom.getEmail(), code);
			return new ResponseEntity<>(new MessageDTO("confirm ticket transaction by email"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/transfer/validate-transfer/{code}")
	public ResponseEntity<?> finishTransferTicket(@PathVariable String code) {
		Transaction transaction = ticketServices.findTransactionById(code);
		if (transaction == null)
			return new ResponseEntity<>(new MessageDTO("transaction not found"), HttpStatus.NOT_FOUND);
		if (transaction.getFinishedAt() != null)
			return new ResponseEntity<>(new MessageDTO("transaction already confirmed"), HttpStatus.CONFLICT);

		Ticket ticket = transaction.getTicket();
		if (ticket == null)
			return new ResponseEntity<>(new MessageDTO("this transaction has not been accepted"), HttpStatus.NOT_FOUND);
		if (ticketServices.isTicketUsed(transaction.getTicket()))
			return new ResponseEntity<>(new MessageDTO("ticket has already been used"), HttpStatus.CONFLICT);

		// Validate that acceptExpiresAt hasn't happened
		Date currentDate = new Date();
		if (transaction.getAcceptExpiresAt().compareTo(currentDate) < 0)
			return new ResponseEntity<>(new MessageDTO("transaction code is expired"), HttpStatus.CONFLICT);

		try {
			ticketServices.validateTransfer(transaction);
			return new ResponseEntity<>(new MessageDTO("transaction confirmed"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
