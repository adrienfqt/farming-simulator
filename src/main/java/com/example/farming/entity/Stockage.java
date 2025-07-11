package com.example.farming.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Stockage {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private int capaciteMax;

    @Enumerated(EnumType.STRING)
    private TypeStockage type;

    @Column(nullable = false)
    private boolean principal;

    @ElementCollection
    @CollectionTable(name = "stockage_contenu", joinColumns = @JoinColumn(name = "stockage_id"))
    @MapKeyColumn(name = "produit")
    @Column(name = "quantite")
    private Map<String, Integer> contenu;
}
