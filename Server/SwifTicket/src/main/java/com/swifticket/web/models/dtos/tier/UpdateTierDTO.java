package com.swifticket.web.models.dtos.tier;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class UpdateTierDTO {
    @NotBlank(message = "name is required")
    @Size(min = 3,max = 25 ,message = "Format error: name must be at least 3 characters long")
    private String name;

    @NotBlank(message = "capacity is required")
    @Positive(message = "Value error: capacity must be a positive number")
    private int capacity;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 3, fraction = 2)
    private float price;
}
