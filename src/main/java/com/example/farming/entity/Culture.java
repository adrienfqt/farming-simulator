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
public class Culture {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private int rendement;

    private boolean besoinLabourage;

    @Enumerated(EnumType.STRING)
    private TypeCulture typeCulture;

    @ManyToMany
    private List<Machine> machinesRequises;
}
