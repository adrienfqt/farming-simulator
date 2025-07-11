package com.example.farming.repository;

import com.example.farming.entity.Stockage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StockageRepository extends JpaRepository<Stockage, UUID> {
    Optional<Stockage> findByType(String type);

    @Override
    Optional<Stockage> findById(UUID uuid);
}
