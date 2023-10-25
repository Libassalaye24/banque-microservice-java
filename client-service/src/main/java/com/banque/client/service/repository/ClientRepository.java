package com.banque.client.service.repository;

import com.banque.client.service.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client , UUID> {
    Optional<Client> findByTelephone(String telephone);   

}
