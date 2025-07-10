package com.example.farming.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Champ {

    @Id
    @GeneratedValue
    private UUID id;

    private int numero;

    private EtatChamp etat;

    @ManyToOne
    private Culture culture;

    @ManyToOne
    private Lot lot;

    private LocalDateTime debutSemis;






}
