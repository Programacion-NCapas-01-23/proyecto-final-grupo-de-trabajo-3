package com.swifticket.web.repositories;

import com.swifticket.web.models.entities.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SponsorRepository extends JpaRepository<Sponsor, Integer> {
    Sponsor findOneByName(String name);
    Sponsor findOneByNameAndImage(String name, String image);
}
