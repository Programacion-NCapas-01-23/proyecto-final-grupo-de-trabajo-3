package com.swifticket.web.models.dtos.tier;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateTierDTO {
    @NotBlank(message = "name is required")
    @Size(min = 3,max = 25 ,message = "Format error: name must be at least 3 characters long")
    private String name;

    @NotBlank(message = "capacity is required")
    @Positive(message = "Value error: capacity must be a positive number")
    private int capacity;

    @NotBlank(message = "price is required")
    @Positive(message = "Value error: price must be a positive number")
    private int price;
}
