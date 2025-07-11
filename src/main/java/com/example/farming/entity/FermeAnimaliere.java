package com.example.farming.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class FermeAnimaliere {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TypeAnimal type;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Animal> animaux;

    @OneToOne
    private Champ champ;

    private java.time.LocalDateTime dateCreation;
} 