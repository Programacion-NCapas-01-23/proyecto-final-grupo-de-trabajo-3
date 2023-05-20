package com.swifticket.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SwifTicketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwifTicketApplication.class, args);
    }

}
