package com.swifticket.web.models.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestValidationToken {
    @NotBlank(message = "email is required")
    @Email(message = "Format error: email format is required")
    private String email;
}
