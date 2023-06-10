package com.swifticket.web.repositories;

import com.swifticket.web.models.entities.Transaction;
import org.springframework.data.repository.ListCrudRepository;
import java.util.UUID;

public interface TransactionRepository extends ListCrudRepository<Transaction, UUID> {

}
