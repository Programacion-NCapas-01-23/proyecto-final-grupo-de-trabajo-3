package com.swifticket.web.models.dtos.tier;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SaveTierDTO {
    @NotBlank(message = "event ID is required")
    private String eventId;

    @NotBlank(message = "name is required")
    @Size(min = 3, max = 25, message = "Format error: The name of the tier must be less than 25 characters and more than 3 characters")
    private String name;

    @Positive(message = "Value error: capacity must be a positive number")
    private int capacity;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 3, fraction = 2)
    private float price;
}
