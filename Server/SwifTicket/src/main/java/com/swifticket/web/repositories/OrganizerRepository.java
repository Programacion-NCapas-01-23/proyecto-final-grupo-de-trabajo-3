package com.swifticket.web.repositories;

import com.swifticket.web.models.entities.Organizer;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;;

public interface OrganizerRepository extends JpaRepository<Organizer, Integer> {
    Organizer findOneByName(String name);
    Page<Organizer> findByNameContains(String name, Pageable pageable);
}
