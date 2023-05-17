package com.swifticket.web.repositories;

import org.springframework.data.repository.ListCrudRepository;
import com.swifticket.web.models.entities.Category;

public interface CategoryRepository extends ListCrudRepository<Category, Integer> {

}
