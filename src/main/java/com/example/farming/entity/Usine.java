package com.example.farming.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Usine {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String nom;

    @ElementCollection
    @CollectionTable(name = "usine_intrants", joinColumns = @JoinColumn(name = "usine_id"))
    @MapKeyColumn(name = "produit")
    @Column(name = "quantite")
    private Map<String, Integer> intrants;

    @Column(nullable = false)
    private String produitFinal;

    @Column(nullable = false)
    private int multiplicateur;
}
