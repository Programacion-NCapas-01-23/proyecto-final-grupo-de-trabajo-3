package com.swifticket.web.models.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
// DTO just to get THE RESPONSE CODE data and not to set it
public class CodeDTO {
    private String code;
}
