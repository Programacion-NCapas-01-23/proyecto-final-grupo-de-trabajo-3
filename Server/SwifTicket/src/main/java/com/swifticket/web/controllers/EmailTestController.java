package com.swifticket.web.controllers;

import com.swifticket.web.services.EmailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailTestController {
    @Autowired
    private EmailServices emailServices;

    @Value("${test-email}")
    private String email;

    @Value("${test-code}")
    private String code;

    @GetMapping("")
    public ResponseEntity<?> sendTest() {
        emailServices.sendVerificationTransactionCode(email, code);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
