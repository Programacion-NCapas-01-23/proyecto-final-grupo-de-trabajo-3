package com.swifticket.web.repositories;

import com.swifticket.web.models.entities.Avatar;
import org.springframework.data.repository.ListCrudRepository;

public interface AvatarRepository extends ListCrudRepository<Avatar, Integer> {
}
