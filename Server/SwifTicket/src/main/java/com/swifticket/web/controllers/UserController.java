package com.swifticket.web.controllers;

import com.swifticket.web.models.dtos.event.ChangeEventStatusDTO;
import com.swifticket.web.models.dtos.response.MessageDTO;
import com.swifticket.web.models.dtos.user.ToggleStatusDTO;
import com.swifticket.web.models.dtos.user.UserDTO;
import com.swifticket.web.models.entities.Avatar;
import com.swifticket.web.models.entities.Event;
import com.swifticket.web.models.entities.User;
import com.swifticket.web.services.AvatarServices;
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
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

	private final UserServices userService;
	private final AvatarServices avatarServices;
	private final ErrorHandler errorHandler;

	@Autowired
	public UserController(UserServices userService, AvatarServices avatarServices, ErrorHandler errorHandler) {
		this.userService = userService;
		this.avatarServices = avatarServices;
		this.errorHandler = errorHandler;
	}

	@GetMapping("")
	public ResponseEntity<?>getUsers() {
		List<User> users = userService.findAll();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getUser(@PathVariable String id) {
		User user = userService.findOneById(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<?> registerUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}
		try {
			userService.register(userDTO.getName(), userDTO.getEmail(), userDTO.getEncryptedPass(), userDTO.getAvatar());
			return new ResponseEntity<>(new MessageDTO("User registered"), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody @Valid UserDTO userDTO,
										BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}
		User user = userService.findOneById(id);
		if (user == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		Avatar avatar = avatarServices.findById(userDTO.getAvatar().getId());
		if (avatar == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		try {
			userService.update(id, userDTO.getName(), avatar);
			return new ResponseEntity<>(new MessageDTO("User updated"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/change-password/{id}")
	public ResponseEntity<?> changePassword(@PathVariable String id, @RequestBody @Valid UserDTO userDTO,
											BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}
		User user = userService.findOneById(id);
		if (user == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		try {
			// TODO: CHECK - Receive old password and check if it matches the one in the database then update the password
			userService.changePassword(id, userDTO.getEncryptedPass());
			return new ResponseEntity<>(new MessageDTO("Password changed"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PatchMapping("/toggle-status")
	public ResponseEntity<?> toggleStatus(@ModelAttribute ToggleStatusDTO data) {
		User user = userService.findOneById(data.getUserId());
		if (user == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		try {
			userService.toggleStatus(data.getUserId(), data.getStatus());
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/role")
	public ResponseEntity<?> assignRole() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/role")
	public ResponseEntity<?> removeRole() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@PostMapping("/assign-to-event")
	public ResponseEntity<?> assignToEvent() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/remove-from-event")
	public ResponseEntity<?> removeFromEvent() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
