package com.swifticket.web.repositories;

import com.swifticket.web.models.entities.SystemState;
import org.springframework.data.repository.ListCrudRepository;

public interface SystemStateRepository extends ListCrudRepository<SystemState, Integer> {
}
