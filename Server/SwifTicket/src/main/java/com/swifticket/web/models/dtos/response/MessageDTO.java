package com.swifticket.web.models.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
// DTO just to get THE RESPONSE MESSAGE data and not to set it
public class MessageDTO {
	private String message;
}
