package com.swifticket.web.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private int avatarId;
    private String name;
    private String email;
}
