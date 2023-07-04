package com.swifticket.web.repositories;

import com.swifticket.web.models.entities.AuthToken;
import com.swifticket.web.models.entities.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.UUID;

public interface AuthTokenRepository extends ListCrudRepository<AuthToken, UUID> {
    List<AuthToken> findByUserAndActive(User user, Boolean active);
}
