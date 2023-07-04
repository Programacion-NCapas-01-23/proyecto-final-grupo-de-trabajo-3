package com.swifticket.web.repositories;

import com.swifticket.web.models.entities.Bill;
import org.springframework.data.repository.ListCrudRepository;
import java.util.UUID;

public interface BillRepository extends ListCrudRepository<Bill, UUID> {

}
