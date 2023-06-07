package com.swifticket.web.models.dtos.tier;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveTierDTO {
    @NotBlank(message = "event ID is required")
    private String eventId;

    @NotBlank(message = "name is required")
    @Size(min = 3, max = 25, message = "Format error: The name of the tier must be less than 25 characters and more than 3 characters")
    private String name;

    @NotBlank(message = "capacity is required")
    @Positive(message = "Value error: capacity must be a positive number")
    private int capacity;

    @NotBlank(message = "price is required")
    @Positive(message = "Value error: price must be a positive number")
    private int price;
}
