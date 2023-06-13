package com.swifticket.web.repositories;

import com.swifticket.web.models.entities.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;;

public interface OrganizerRepository extends JpaRepository<Organizer, Integer> {
    Organizer findOneByName(String name);
}
