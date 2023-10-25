package com.banque.transaction.service.repository;

import com.banque.transaction.service.entity.CompteClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompteRepository extends JpaRepository<CompteClient , UUID> {
    Optional<CompteClient> findByClientId(UUID clientId);
    Optional<CompteClient> findByNumber(String number);
    Optional<List<CompteClient>> findAllByIsActive(boolean isActive);

}
