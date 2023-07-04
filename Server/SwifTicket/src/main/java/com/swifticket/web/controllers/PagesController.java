package com.swifticket.web.controllers;

import com.swifticket.web.models.dtos.response.MessageDTO;
import com.swifticket.web.models.entities.Ticket;
import com.swifticket.web.models.entities.Transaction;
import com.swifticket.web.models.entities.User;
import com.swifticket.web.models.entities.UserState;
import com.swifticket.web.services.AuthServices;
import com.swifticket.web.services.TicketServices;
import com.swifticket.web.services.UserServices;
import com.swifticket.web.services.UserStateServices;
import com.swifticket.web.utils.UserStateCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

@Controller
public class PagesController {
    @Autowired
    private AuthServices authServices;
    @Autowired
    private UserStateServices userStateServices;
    @Autowired
    private UserServices userServices;
    @Autowired
    private TicketServices ticketServices;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "swifticket");
        return "message";
    }

    @GetMapping("/validate-account/{code}")
    public String validateAccount(@PathVariable String code, Model model) {
        try {
            User user = authServices.validateAccount(code);
            if (user == null) {
                model.addAttribute("message", "invalid validation code");
                return "message";
            }
            // Update user state to Active
            UserState state = userStateServices.findById(UserStateCatalog.ACTIVE);
            userServices.toggleStatus(user, state);

            model.addAttribute("message", "account validated");
            return "message";
        } catch (Exception e) {
            model.addAttribute("message", "Something happened, try again later");
            return "message";
        }
    }

    @GetMapping("/validate-transfer/{code}")
    public String finishTransferTicket(@PathVariable String code, Model model) {
        Transaction transaction = ticketServices.findTransactionById(code);
        if (transaction == null) {
            model.addAttribute("message", "transaction not found");
            return "message";
        }

        if (transaction.getFinishedAt() != null) {
            model.addAttribute("message", "transaction already confirmed");
            return "message";
        }

        Ticket ticket = transaction.getTicket();
        if (ticket == null) {
            model.addAttribute("message", "this transaction has not been accepted");
            return "message";
        }

        if (ticketServices.isTicketUsed(transaction.getTicket())) {
            model.addAttribute("message", "ticket has already been used");
            return "message";
        }

        // Validate that acceptExpiresAt hasn't happened
        Date currentDate = new Date();
        if (transaction.getAcceptExpiresAt().compareTo(currentDate) < 0) {
            model.addAttribute("message", "transaction code is expired");
            return "message";
        }

        try {
            ticketServices.validateTransfer(transaction);

            model.addAttribute("message", "transaction confirmed");
            return "message";
        } catch (Exception e) {
            model.addAttribute("message", "Something happened, try again later");
            return "message";
        }
    }
}
