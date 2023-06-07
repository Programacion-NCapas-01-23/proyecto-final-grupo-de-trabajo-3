package com.swifticket.web.models.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
// DTO just to get THE TOKEN data and not to set it
public class ValidateTokenDTO {
	private String token;
}
