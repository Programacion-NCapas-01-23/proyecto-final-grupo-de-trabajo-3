package com.swifticket.web.repositories;

import com.swifticket.web.models.entities.Tier;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface TierRepository extends ListCrudRepository<Tier, UUID> {
}
