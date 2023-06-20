package com.swifticket.web.models.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GoogleUserDTO {
    private String email;
    private String name;
}
