package com.banque.transaction.service.repository;

import com.banque.transaction.service.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction , UUID> {
    Optional<Transaction> findAllByClientId(UUID clientId);
}
