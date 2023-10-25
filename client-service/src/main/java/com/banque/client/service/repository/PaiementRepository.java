package com.banque.client.service.repository;

import com.banque.client.service.entity.Client;
import com.banque.client.service.entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement , UUID> {
    Optional<List<Paiement>> findByClient(Client client);
}
