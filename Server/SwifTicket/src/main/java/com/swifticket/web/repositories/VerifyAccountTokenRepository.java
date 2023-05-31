package com.swifticket.web.repositories;

import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.swifticket.web.models.entities.VerifyAccountToken;

public interface VerifyAccountTokenRepository extends ListCrudRepository<VerifyAccountToken, UUID> {

}
