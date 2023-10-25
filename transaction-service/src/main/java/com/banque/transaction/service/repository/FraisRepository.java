package com.banque.transaction.service.repository;
import com.banque.transaction.service.entity.Frais;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.UUID;

@Repository
public interface FraisRepository extends JpaRepository<Frais , UUID> {
    
}
