package com.swifticket.web.models.dtos.user;

import com.swifticket.web.models.entities.Avatar;
import com.swifticket.web.models.entities.Role;
import com.swifticket.web.models.entities.User;
import com.swifticket.web.models.entities.UserState;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
// DTO only to get necessary data from user not to set it
public class UserDTO {
    private UUID id;
    private String name;
    private String email;
    private UserState state;
    private Avatar avatar;
    private List<Role> roles;
    private Date createdAt;

    public UserDTO(User user, List<Role> roles) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.state = user.getState();
        this.avatar = user.getAvatar();
        this.roles = roles;
        this.createdAt = user.getCreatedAt();
    }
}
