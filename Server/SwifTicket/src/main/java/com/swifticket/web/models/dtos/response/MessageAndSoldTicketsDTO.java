package com.swifticket.web.models.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageAndSoldTicketsDTO {
    private String message;
    private int soldTickets;
}
