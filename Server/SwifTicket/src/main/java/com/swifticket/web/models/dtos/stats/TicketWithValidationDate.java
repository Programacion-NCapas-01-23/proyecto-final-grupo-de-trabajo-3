package com.swifticket.web.models.dtos.stats;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
// DTO just to get the validation date data and not to set it
public class TicketWithValidationDate {
    private UUID id;
    private Date validatedAt;
}
