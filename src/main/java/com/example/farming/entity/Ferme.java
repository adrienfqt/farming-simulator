package com.example.farming.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Ferme {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Champ> champs;

    @ManyToOne
    private Stockage stockage;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Machine> machines;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Usine> usines;
}
