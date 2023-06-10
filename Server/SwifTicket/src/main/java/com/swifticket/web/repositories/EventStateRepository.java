package com.swifticket.web.repositories;

import com.swifticket.web.models.entities.EventState;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

public interface EventStateRepository extends ListCrudRepository<EventState, Integer>{
    @Query("SELECT es FROM EventState es WHERE es.name = :name")
    EventState findByState(@Param("name") String name);
}
