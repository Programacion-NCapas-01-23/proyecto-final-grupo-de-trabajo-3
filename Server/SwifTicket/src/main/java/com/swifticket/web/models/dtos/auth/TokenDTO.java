package com.swifticket.web.models.dtos.auth;

import com.swifticket.web.models.entities.AuthToken;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDTO {
    private String token;
    public TokenDTO(AuthToken authToken) {
        this.token = authToken.getContent();
    }
}
