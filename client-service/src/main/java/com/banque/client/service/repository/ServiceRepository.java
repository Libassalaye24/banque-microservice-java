package com.banque.client.service.repository;

import com.banque.client.service.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceRepository extends JpaRepository<Service , UUID> {
    Optional<Service> findByLibelle(String libelle);
}
