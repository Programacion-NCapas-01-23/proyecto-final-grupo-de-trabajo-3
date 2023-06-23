package com.swifticket.web.controllers;

import com.swifticket.web.models.entities.User;
import com.swifticket.web.services.UserServices;
import com.swifticket.web.utils.RoleCatalog;
import com.swifticket.web.utils.RoleVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swifticket.web.models.dtos.response.MessageDTO;
import com.swifticket.web.models.dtos.response.StateDTO;
import com.swifticket.web.services.SystemStateService;

@RestController
@RequestMapping("/system")
@CrossOrigin("*")
public class SystemController {

	private final int UNSET = -1;
	private final SystemStateService systemService;
	private final UserServices userServices;
	@Autowired
	public SystemController(SystemStateService systemService, UserServices userServices) {
		this.systemService = systemService;
		this.userServices = userServices;
	}

	@PostMapping("/suspend-service")
	public ResponseEntity<?> suspendService() {
		User authUser = userServices.findUserAuthenticated();
		// Grant access by user's role -> ADMIN, SUPER_ADMIN
		int[] validRoles = {RoleCatalog.ADMIN, RoleCatalog.SUPER_ADMIN};
		if (!RoleVerifier.userMatchesRoles(validRoles, userServices.getUserRoles(authUser)))
			return new ResponseEntity<>(new MessageDTO("Credential permissions not valid"), HttpStatus.UNAUTHORIZED);

		int state = systemService.getStatus();
		if (state == UNSET)
			return new ResponseEntity<>(
					new MessageDTO("system state is not defined"),
					HttpStatus.INTERNAL_SERVER_ERROR);

		try {
			systemService.toggleStatus();
			return new ResponseEntity<>(
					new MessageDTO("status updated"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/system-state")
	public ResponseEntity<?> getSystemState() {
		int state = systemService.getStatus();
		if (state == UNSET)
			return new ResponseEntity<>(
					new MessageDTO("system state is not defined"),
					HttpStatus.INTERNAL_SERVER_ERROR);

		return new ResponseEntity<>(new StateDTO(state), HttpStatus.OK);
	}
}
