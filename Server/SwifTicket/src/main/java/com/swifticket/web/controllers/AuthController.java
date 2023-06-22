package com.swifticket.web.controllers;

import java.util.ArrayList;
import java.util.List;

import com.swifticket.web.models.dtos.auth.*;
import com.swifticket.web.models.dtos.user.CreateUserDTO;
import com.swifticket.web.models.dtos.user.RequestValidationToken;
import com.swifticket.web.models.entities.*;
import com.swifticket.web.services.*;
import com.swifticket.web.utils.RoleCatalog;
import com.swifticket.web.utils.UserStateCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.swifticket.web.models.dtos.response.MessageDTO;
import com.swifticket.web.utils.ErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
private final AuthServices authServices;
	private final UserServices userServices;
	private final AvatarServices avatarServices;
	private final RoleServices roleServices;
	private final UserStateServices userStateServices;
	private final ErrorHandler errorHandler;
	private final EmailServices emailServices;

	@Autowired
	public AuthController(AuthServices authServices, UserServices userServices, AvatarServices avatarServices, RoleServices roleServices, UserStateServices userStateServices, ErrorHandler errorHandler, EmailServices emailServices) {
		this.authServices = authServices;
		this.userServices = userServices;
		this.avatarServices = avatarServices;
		this.roleServices = roleServices;
		this.userStateServices = userStateServices;
		this.errorHandler = errorHandler;
		this.emailServices = emailServices;
	}

	@PostMapping("/google/signin")
	public ResponseEntity<?> registerAndLoginUser(@ModelAttribute @Valid IdTokenRequestDTO data, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		try {
			User user = authServices.loginOAuthGoogle(data);
			if (user == null)
				return new ResponseEntity<>(new MessageDTO("invalid credentials"), HttpStatus.UNAUTHORIZED);

			// if new user return user role
			Role role = roleServices.findById(RoleCatalog.USER);
			List<Role> roles = new ArrayList<>();
			roles.add(role);

			// if user exists get user roles
			List<RolexUser> rolesRelations = user.getRolexUsers();
			if (rolesRelations != null)
				roles = rolesRelations.stream().map(RolexUser::getRole).toList();

			// If the user is new then send verification code to it's email
			if (user.getIsNewUser()) {
				String code = authServices.generateVerifyAccountToken(user.getEmail());
				emailServices.sendVerificationAccountCode(user.getEmail(), code);
			}

			// Create and register users JWT
			AuthToken authToken = userServices.registerToken(user);

			SignedInUserDTO response = new SignedInUserDTO(
					user.getId().toString(),
					user.getName(),
					user.getEmail(),
					user.getAvatar().getImage(),
					user.getState().getName(),
					roles,
					authToken);

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			// System.out.println("ERROR " + e.getMessage() + e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
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

		// When user's signs up it's state is UNVERIFIED
		UserState state = userStateServices.findById(UserStateCatalog.UNVERIFIED);
		if (state == null)
			return new ResponseEntity<>(new MessageDTO("state not found"), HttpStatus.NOT_FOUND);

		User userByEmail = userServices.findOneByEmail(userData.getEmail());
		if (userByEmail != null)
			return new ResponseEntity<>(new MessageDTO("email is already taken"), HttpStatus.CONFLICT);

		try {
			userServices.register(userData.getName(), userData.getEmail(), userData.getPassword(), avatar, state);
			// Send verification code to user
			String code = authServices.generateVerifyAccountToken(userData.getEmail());
			emailServices.sendVerificationAccountCode(userData.getEmail(), code);

			return new ResponseEntity<>(new MessageDTO("User registered"), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/validate-token")
	public ResponseEntity<?> validateAuthToken(@ModelAttribute ValidateTokenDTO data) {
		return new ResponseEntity<>(new MessageDTO("valid auth token"), HttpStatus.OK);
	}
	
	@GetMapping("/validate-account/{code}")
	public ResponseEntity<?> validateAccount(@PathVariable String code) {
		try {
			User user = authServices.validateAccount(code);
			if (user == null)
				return new ResponseEntity<>(new MessageDTO("invalid validation code"), HttpStatus.NOT_FOUND);

			// Update user state to Active
			UserState state = userStateServices.findById(UserStateCatalog.ACTIVE);
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
		// Check if user's has already been verified
		if (user.getState().getId() != UserStateCatalog.UNVERIFIED)
			return new ResponseEntity<>(new MessageDTO("user already validated"), HttpStatus.CONFLICT);

		try {
			String code = authServices.generateVerifyAccountToken(data.getEmail());
			emailServices.sendVerificationAccountCode(user.getEmail(), code);

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
			return new ResponseEntity<>(new MessageDTO("invalid credentials"), HttpStatus.UNAUTHORIZED);

		List<RolexUser> rolesRelations = user.getRolexUsers();
		List<Role> roles = rolesRelations.stream().map(RolexUser::getRole).toList();

		try {
			AuthToken authToken = userServices.registerToken(user);
			SignedInUserDTO response = new SignedInUserDTO(
					user.getId().toString(),
					user.getName(),
					user.getEmail(),
					user.getAvatar().getImage(),
					user.getState().getName(),
					roles,
					authToken);

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
