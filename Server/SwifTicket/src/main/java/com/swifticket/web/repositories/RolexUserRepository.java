package com.swifticket.web.repositories;

import com.swifticket.web.models.entities.Role;
import com.swifticket.web.models.entities.RolexUser;
import com.swifticket.web.models.entities.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface RolexUserRepository extends ListCrudRepository<RolexUser, UUID> {
    RolexUser findOneByRoleAndUser(Role role, User user);
}
