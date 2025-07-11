package com.example.farming.repository;

import com.example.farming.entity.Champ;
import com.example.farming.entity.EtatChamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChampRepository extends JpaRepository<Champ, UUID> {


    Optional<Champ> findByEtat(EtatChamp etat);

    Optional<Champ> findFirstByEtat(EtatChamp etat);

    @Override
    Optional<Champ> findById(UUID uuid);
}
