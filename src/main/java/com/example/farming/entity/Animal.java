package com.example.farming.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Animal {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TypeAnimal type;

    private double stockHerbe;

    private boolean vivant;

    private double productionAccumulee;

    private double productionFumier;

    private java.time.LocalDateTime dateDeces;

    private java.time.LocalDateTime dateEntreeFerme;

    private String nom;
} 