package com.swifticket.web.controllers;

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
	
	@Autowired
	private SystemStateService systemService;
	
	@PostMapping("/suspend-service")
	public ResponseEntity<?> suspendService() {
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
		return new ResponseEntity<>(new StateDTO(state), HttpStatus.OK);
	}

}