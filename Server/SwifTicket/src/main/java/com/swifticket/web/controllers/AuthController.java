package com.swifticket.web.controllers;

import java.util.List;

import com.swifticket.web.models.dtos.user.CreateUserDTO;
import com.swifticket.web.models.dtos.user.RequestValidationToken;
import com.swifticket.web.models.dtos.user.UserDTO;
import com.swifticket.web.models.entities.*;
import com.swifticket.web.services.AvatarServices;
import com.swifticket.web.services.UserServices;
import com.swifticket.web.services.UserStateServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.swifticket.web.models.dtos.auth.SignInDTO;
import com.swifticket.web.models.dtos.auth.SignedInUserDTO;
import com.swifticket.web.models.dtos.auth.ValidateTokenDTO;
import com.swifticket.web.models.dtos.response.MessageDTO;
import com.swifticket.web.services.AuthServices;
import com.swifticket.web.utils.ErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
private final AuthServices authServices;
	private final UserServices userServices;
	private final AvatarServices avatarServices;
	private final UserStateServices userStateServices;
	private final ErrorHandler errorHandler;
	@Autowired
	public AuthController(AuthServices authServices, UserServices userServices, AvatarServices avatarServices, UserStateServices userStateServices, ErrorHandler errorHandler) {
		this.authServices = authServices;
		this.userServices = userServices;
		this.avatarServices = avatarServices;
		this.userStateServices = userStateServices;
		this.errorHandler = errorHandler;
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@ModelAttribute @Valid CreateUserDTO userData, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		Avatar avatar = avatarServices.findById(userData.getAvatarId());
		if (avatar == null)
			return new ResponseEntity<>(new MessageDTO("invalid avatar"), HttpStatus.NOT_FOUND);

		UserState state = userStateServices.findById(3);
		if (state == null)
			return new ResponseEntity<>(new MessageDTO("state not found"), HttpStatus.NOT_FOUND);

		User userByEmail = userServices.findOneByEmail(userData.getEmail());
		if (userByEmail != null)
			return new ResponseEntity<>(new MessageDTO("email is already taken"), HttpStatus.BAD_REQUEST);

		try {
			userServices.register(userData.getName(), userData.getEmail(), userData.getPassword(), avatar, state);
			String code = authServices.generateVerifyAccountToken(userData.getEmail());
			// TODO: send code to user via email

			return new ResponseEntity<>(new MessageDTO("User registered"), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/validate-token")
	public ResponseEntity<?> validateAuthToken(@ModelAttribute ValidateTokenDTO data) {
		// TODO: When using spring security this method will be DEPRECATED
		return new ResponseEntity<>(new MessageDTO("valid auth token"), HttpStatus.OK);
	}
	
	@GetMapping("/validate-account/{code}")
	public ResponseEntity<?> validateAccount(@PathVariable String code) {
		try {
			User user = authServices.validateAccount(code);
			if (user == null)
				return new ResponseEntity<>(new MessageDTO("invalid validation code"), HttpStatus.NOT_FOUND);

			// Update user state to Active
			UserState state = userStateServices.findById(1);
			userServices.toggleStatus(user, state);

			return new ResponseEntity<>(new MessageDTO("account validated"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/validate-account")
	public ResponseEntity<?> requestValidationAccountToken(
			@ModelAttribute RequestValidationToken data, BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		User user = userServices.findOneByEmail(data.getEmail());
		if (user == null)
			return new ResponseEntity<>(new MessageDTO("user not found"), HttpStatus.NOT_FOUND);
		if (user.getState().getId() != 3)
			return new ResponseEntity<>(new MessageDTO("user already validated"), HttpStatus.CONFLICT);

		try {
			String code = authServices.generateVerifyAccountToken(data.getEmail());
			// TODO : send code to user via email

			return new ResponseEntity<>(new MessageDTO("validation code sent to email"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> signIn(@ModelAttribute @Valid SignInDTO data, BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}
		
		User user = authServices.signIn(data.getEmail(), data.getPassword());
		if (user == null)
			return new ResponseEntity<>(new MessageDTO("invalid credentials"), HttpStatus.NOT_FOUND);

		List<RolexUser> rolesRelations = user.getRolexUsers();
		List<Role> roles = rolesRelations.stream().map(RolexUser::getRole).toList();
		
		SignedInUserDTO response = new SignedInUserDTO(
				user.getId().toString(), 
				user.getName(), 
				user.getEmail(), 
				user.getAvatar().getImage(),
				roles);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
