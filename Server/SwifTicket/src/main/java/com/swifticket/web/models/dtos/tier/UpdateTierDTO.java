package com.swifticket.web.models.dtos.tier;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateTierDTO {
    @NotBlank(message = "name is required")
    private String name;

    @Positive(message = "capacity must be a positive number")
    private int capacity;

    @Positive(message = "price must be a positive number")
    private int price;
}
