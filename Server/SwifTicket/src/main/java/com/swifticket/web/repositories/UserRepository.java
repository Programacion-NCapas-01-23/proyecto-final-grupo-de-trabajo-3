package com.swifticket.web.repositories;

import com.swifticket.web.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findOneByEmail(String email);
    User findByIdOrEmail(UUID id, String email);
}
