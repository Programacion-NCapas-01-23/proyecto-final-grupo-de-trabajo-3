package com.swifticket.web.controllers;

import com.swifticket.web.models.dtos.response.MessageDTO;
import com.swifticket.web.models.dtos.user.*;
import com.swifticket.web.models.entities.*;
import com.swifticket.web.services.*;
import com.swifticket.web.utils.ErrorHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

	private final UserServices userService;
	private final AvatarServices avatarServices;
	private final RoleServices roleServices;
	private final UserStateServices userStateServices;
	private final EventServices eventServices;
	private final ErrorHandler errorHandler;

	@Autowired
	public UserController(UserServices userService, AvatarServices avatarServices, RoleServices roleServices, UserStateServices userStateServices, EventServices eventServices, ErrorHandler errorHandler) {
		this.userService = userService;
		this.avatarServices = avatarServices;
		this.roleServices = roleServices;
		this.userStateServices = userStateServices;
		this.eventServices = eventServices;
		this.errorHandler = errorHandler;
	}

	@GetMapping("")
	public ResponseEntity<?>getUsers() {
		List<User> users = userService.findAll();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getUser(@PathVariable String id) {
		User user = userService.findOneByEmail(id);
		if (user == null)
			return new ResponseEntity<>(new MessageDTO("user not found"), HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(
			@PathVariable String id, @ModelAttribute @Valid UpdateUserDTO data, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		User user = userService.findOneByEmail(id);
		if (user == null)
			return new ResponseEntity<>(new MessageDTO("user not found"), HttpStatus.NOT_FOUND);

		// Cast to int to avoid null pointer exception
		Avatar avatar;
		if (data.getAvatar() < 1) {
			avatar = avatarServices.findById(1);
		} else {
			avatar = avatarServices.findById(data.getAvatar());
		}

		if (avatar == null)
			return new ResponseEntity<>(new MessageDTO("avatar not found"), HttpStatus.NOT_FOUND);

		try {
			userService.update(user, data, avatar);
			return new ResponseEntity<>(new MessageDTO("User updated"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/change-password")
	public ResponseEntity<?> changePassword(
			@ModelAttribute @Valid ChangePasswordDTO data, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		User user = userService.findOneByEmail(data.getEmail());
		if (user == null)
			return new ResponseEntity<>(new MessageDTO("invalid credentials"), HttpStatus.NOT_FOUND);

		try {
			Boolean response = userService.changePassword(user, data);
			if (!response)
				return new ResponseEntity<>(new MessageDTO("invalid credentials!"), HttpStatus.NOT_FOUND);

			return new ResponseEntity<>(new MessageDTO("password changed"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PatchMapping("/toggle-status")
	public ResponseEntity<?> toggleStatus(
			@ModelAttribute @Valid ToggleStatusDTO data, BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		User user = userService.findOneByEmail(data.getUserId());
		if (user == null)
			return new ResponseEntity<>(new MessageDTO("user not found"), HttpStatus.NOT_FOUND);

		UserState state = userStateServices.findById(data.getState());
		if (state == null)
			return new ResponseEntity<>(new MessageDTO("user state not found"), HttpStatus.NOT_FOUND);

		try {
			userService.toggleStatus(user, state);
			return new ResponseEntity<>(new MessageDTO("user state updated"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/role")
	public ResponseEntity<?> assignRole(
			@ModelAttribute @Valid AssignRoleDTO data, BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		User user = userService.findOneByEmail(data.getUserId());
		if (user == null)
			return new ResponseEntity<>(new MessageDTO("user not found"), HttpStatus.NOT_FOUND);

		Role role = roleServices.findById(data.getRole());
		if (role == null)
			return new ResponseEntity<>(new MessageDTO("role not found"), HttpStatus.NOT_FOUND);

		RolexUser relation = userService.findByRoleAndUser(user, role);
		if (relation != null) // TODO: check this response status code
			return new ResponseEntity<>(new MessageDTO("role is already assigned to user"), HttpStatus.OK);

		try {
			userService.assignRole(user, role);
			return new ResponseEntity<>(new MessageDTO("role assigned successfully"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/role")
	public ResponseEntity<?> removeRole(
			@ModelAttribute @Valid RemoveRoleDTO data, BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		User user = userService.findOneByEmail(data.getUserId());
		if (user == null)
			return new ResponseEntity<>(new MessageDTO("user not found"), HttpStatus.NOT_FOUND);

		Role role = roleServices.findById(data.getRole());
		if (role == null)
			return new ResponseEntity<>(new MessageDTO("role not found"), HttpStatus.NOT_FOUND);

		RolexUser relation = userService.findByRoleAndUser(user, role);
		if (relation == null) // TODO: check this response status code
			return new ResponseEntity<>(new MessageDTO("role wasn't assigned to user"), HttpStatus.OK);

		try {
			userService.removeRole(user, role);
			return new ResponseEntity<>(new MessageDTO("role removed successfully"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/assign-to-event")
	public ResponseEntity<?> assignToEvent(
			@ModelAttribute @Valid AssignUserToEventDTO data, BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		User user = userService.findOneByEmail(data.getUserId());
		if (user == null)
			return new ResponseEntity<>(new MessageDTO("user not found"), HttpStatus.NOT_FOUND);

		Event event = eventServices.findById(data.getEventId());
		if (event == null)
			return new ResponseEntity<>(new MessageDTO("event not found"), HttpStatus.NOT_FOUND);

		EventxValidator relation = userService.findByEventAndUser(event, user);
		if (relation != null) // TODO: check this response status code
			return new ResponseEntity<>(new MessageDTO("user is already assigned to event"), HttpStatus.OK);

		try {
			userService.assignToEvent(user, event);
			return new ResponseEntity<>(new MessageDTO("user assigned to event"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/remove-from-event")
	public ResponseEntity<?> removeFromEvent(
			@ModelAttribute @Valid RemoveUserFromEventDTO data, BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		User user = userService.findOneByEmail(data.getUserId());
		if (user == null)
			return new ResponseEntity<>(new MessageDTO("user not found"), HttpStatus.NOT_FOUND);

		Event event = eventServices.findById(data.getEventId());
		if (event == null)
			return new ResponseEntity<>(new MessageDTO("event not found"), HttpStatus.NOT_FOUND);

		EventxValidator relation = userService.findByEventAndUser(event, user);
		if (relation == null) // TODO: check this response status code
			return new ResponseEntity<>(new MessageDTO("user wasn't assigned to event"), HttpStatus.OK);

		try {
			userService.removeFromEvent(user, event);
			return new ResponseEntity<>(new MessageDTO("user removed from event"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
