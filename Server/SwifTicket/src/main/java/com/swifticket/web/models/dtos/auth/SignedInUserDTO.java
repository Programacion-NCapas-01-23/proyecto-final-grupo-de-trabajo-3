package com.swifticket.web.models.dtos.auth;

import java.util.List;

import com.swifticket.web.models.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignedInUserDTO {
	private String id;
	private String name;
    private String email;
    private String avatar;
    private List<Role> roles;
}
