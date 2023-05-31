package com.swifticket.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swifticket.web.models.dtos.auth.SignInDTO;
import com.swifticket.web.models.dtos.auth.SignedInUserDTO;
import com.swifticket.web.models.dtos.auth.ValidateTokenDTO;
import com.swifticket.web.models.dtos.response.MessageDTO;
import com.swifticket.web.models.entities.Role;
import com.swifticket.web.models.entities.User;
import com.swifticket.web.services.AuthServices;
import com.swifticket.web.utils.ErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
	private final AuthServices authServices;
	private final ErrorHandler errorHandler;
	@Autowired
	public AuthController(ErrorHandler errorHandler, AuthServices authServices) {
		this.errorHandler = errorHandler;
		this.authServices = authServices;
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
		
		// TODO: get user roles when n:n table is ready...
		List<Role> roles = null;
		
		SignedInUserDTO response = new SignedInUserDTO(
				user.getId().toString(), 
				user.getName(), 
				user.getEmail(), 
				user.getAvatar().getImage(),
				roles);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
