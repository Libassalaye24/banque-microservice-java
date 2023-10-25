package com.banque.client.service.repository;

import com.banque.client.service.entity.CancelTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CancelTimeRepository extends JpaRepository<CancelTime , UUID> {

    Optional<CancelTime> findByEtat(boolean etat);
}
