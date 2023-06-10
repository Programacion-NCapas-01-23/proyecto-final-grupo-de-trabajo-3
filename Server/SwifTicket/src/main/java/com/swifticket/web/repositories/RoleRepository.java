package com.swifticket.web.repositories;

import org.springframework.data.repository.ListCrudRepository;
import com.swifticket.web.models.entities.Role;

public interface RoleRepository extends ListCrudRepository<Role, Integer> {
}
