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
public class Culture {

    @Id
    @GeneratedValue
    private UUID id;

    private int rendement;

    @ManyToMany
    private List<Machine> machinesRequises;
}
