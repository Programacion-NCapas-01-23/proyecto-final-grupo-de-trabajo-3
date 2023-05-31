package com.swifticket.web.models.dtos.tier;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveTierDTO {
    @NotBlank(message = "Event ID is required")
    private String eventId;

    @NotBlank(message = "Name is required")
    private String name;

    @Positive(message = "Capacity must be a positive number")
    private int capacity;

    @Positive(message = "Price must be a positive number")
    private int price;
}
