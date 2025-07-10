package entity;

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
    private int capaciteMax = 100_000;

    @ElementCollection
    @CollectionTable(name = "stockage_contenu", joinColumns = @JoinColumn(name = "stockage_id"))
    @MapKeyColumn(name = "produit")
    @Column(name = "quantite")
    private Map<String, Integer> contenu;
}
