package com.swifticket.web.controllers;

import com.swifticket.web.services.EmailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@CrossOrigin("*")
public class EmailTestController {
    @Autowired
    private EmailServices emailServices;

    @GetMapping("")
    public ResponseEntity<?> sendTest() {
        emailServices.sendVerificationTransactionCode("eduarmercado4@gmail.com", "69-420");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
