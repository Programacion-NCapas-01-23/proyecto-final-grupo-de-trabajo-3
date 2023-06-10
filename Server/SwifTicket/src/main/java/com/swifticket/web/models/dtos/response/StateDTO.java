package com.swifticket.web.models.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
// DTO just to get THE RESPONSE STATE data and not to set it
public class StateDTO {
	private int status;
}
