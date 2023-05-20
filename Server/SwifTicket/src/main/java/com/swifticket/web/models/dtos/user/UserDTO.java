package com.swifticket.web.models.dtos.user;

import com.swifticket.web.models.entities.Avatar;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private String name;
    private String email;
    private String encryptedPass;
    private Avatar avatar;
}
