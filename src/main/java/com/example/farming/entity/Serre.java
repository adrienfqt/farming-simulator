package com.example.farming.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Serre {
    @Id
    @GeneratedValue
    private UUID id;

    private int rendement;

    private double consommationEau;

    private String produit;

    private boolean enPause;

    private java.time.LocalDateTime dateMiseEnService;

    private int capaciteParCycle;
} 