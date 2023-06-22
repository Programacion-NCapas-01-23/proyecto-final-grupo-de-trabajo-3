package com.swifticket.web.models.dtos.auth;

import java.util.List;

import com.swifticket.web.models.entities.AuthToken;
import com.swifticket.web.models.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
// DTO just to get THE USER data and not to set it
public class SignedInUserDTO {
	private String id;
	private String name;
    private String email;
    private String avatar;
    private String state;
    private List<Role> roles;
    private String token;

    public SignedInUserDTO(String id, String name, String email, String avatar, String state, List<Role> roles, AuthToken authToken) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.state = state;
        this.roles = roles;
        this.token = authToken.getContent();
    }
}
