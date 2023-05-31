package com.swifticket.web.controllers;

import java.util.List;

import com.swifticket.web.models.dtos.user.CreateUserDTO;
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

		UserState state = userStateServices.findById(1);
		if (state == null)
			return new ResponseEntity<>(new MessageDTO("state not found"), HttpStatus.NOT_FOUND);

		User userByEmail = userServices.findOneByEmail(userData.getEmail());
		if (userByEmail != null)
			return new ResponseEntity<>(new MessageDTO("email is already taken"), HttpStatus.BAD_REQUEST);

		try {
			userServices.register(userData.getName(), userData.getEmail(), userData.getPassword(), avatar, state);
			return new ResponseEntity<>(new MessageDTO("User registered"), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/validate-token")
	public ResponseEntity<?> validateToken(@ModelAttribute ValidateTokenDTO data) {
		Boolean response = authServices.validateAccount(data.getToken());
		if (!response)
			return new ResponseEntity<>(new MessageDTO("invalid token"), HttpStatus.UNAUTHORIZED);
		
		return new ResponseEntity<>(new MessageDTO("valid token"), HttpStatus.OK);
	}
	
	@GetMapping("/validate-account/{code}")
	public ResponseEntity<?> validateAccount(@PathVariable String code) {
		Boolean response = authServices.validateAccount(code);
		if (!response)
			return new ResponseEntity<>(new MessageDTO("invalid validation code"), HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(new MessageDTO("account validated"), HttpStatus.OK);
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
