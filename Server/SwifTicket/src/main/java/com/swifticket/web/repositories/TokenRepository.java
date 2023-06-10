package com.swifticket.web.repositories;

import com.swifticket.web.models.entities.Token;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface TokenRepository extends ListCrudRepository<Token, UUID> {
}
