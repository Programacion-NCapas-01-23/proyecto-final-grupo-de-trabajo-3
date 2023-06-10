package com.swifticket.web.models.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
// DTO just to get THE RESPONSE MESSAGE AND SOLD TICKETS data and not to set it
public class MessageAndSoldTicketsDTO {
    private String message;
    private int soldTickets;
}
