package com.swifticket.web.repositories;

import com.swifticket.web.models.entities.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface UserRepository extends ListCrudRepository<User, UUID> {
    User findByEmail(String email);

    //User findByConfirmationCode(String code);
}
