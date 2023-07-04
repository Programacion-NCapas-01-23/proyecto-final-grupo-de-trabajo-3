package com.swifticket.web.models.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
// DTO just to get THE RESPONSE MESSAGE data and not to set it
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageDTO {
	private String field;
	private String message;

	public MessageDTO(String message) {
		this.message = message;
	}

	public MessageDTO(String field, String message) {
		this.field = field;
		this.message = message;
	}
}
