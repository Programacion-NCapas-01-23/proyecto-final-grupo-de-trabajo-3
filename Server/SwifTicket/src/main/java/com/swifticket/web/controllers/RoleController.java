package com.swifticket.web.controllers;

import java.util.List;

import com.swifticket.web.models.dtos.response.MessageDTO;
import com.swifticket.web.models.entities.User;
import com.swifticket.web.services.UserServices;
import com.swifticket.web.utils.RoleCatalog;
import com.swifticket.web.utils.RoleVerifier;
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
	private final RoleServices roleServices;
	private final UserServices userServices;
	@Autowired
	public RoleController(RoleServices roleServices, UserServices userServices) {
		this.roleServices = roleServices;
		this.userServices = userServices;
	}

	@GetMapping("")
	public ResponseEntity<?> getRoles() {
		User authUser = userServices.findUserAuthenticated();
		// Grant access by user's role -> ADMIN, SUPER_ADMIN
		int[] validRoles = {RoleCatalog.ADMIN, RoleCatalog.SUPER_ADMIN};
		if (!RoleVerifier.userMatchesRoles(validRoles, userServices.getUserRoles(authUser)))
			return new ResponseEntity<>(new MessageDTO("Credential permissions not valid"), HttpStatus.UNAUTHORIZED);

		List<Role> roles = roleServices.findAll();
		return new ResponseEntity<>(roles, HttpStatus.OK);
	}
}
