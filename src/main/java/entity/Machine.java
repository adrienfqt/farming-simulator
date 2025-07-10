package entity;

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
public class Machine {

    @Id
    @GeneratedValue
    private UUID id;

    private String nom;

    private boolean disponible;

    @ManyToMany
    private List<Culture> cultures;


}
