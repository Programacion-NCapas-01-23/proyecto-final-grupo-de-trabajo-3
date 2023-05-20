package com.swifticket.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swifticket.web.models.entities.Role;
import com.swifticket.web.services.RoleServices;

@RestController
@RequestMapping("/roles")
@CrossOrigin("*")
public class RoleController {
	@Autowired
	private RoleServices roleServices;
	
	@GetMapping("")
	public ResponseEntity<?> getRoles() {
		List<Role> roles = roleServices.findAll();
		return new ResponseEntity<>(roles, HttpStatus.OK);
	}
	
}
