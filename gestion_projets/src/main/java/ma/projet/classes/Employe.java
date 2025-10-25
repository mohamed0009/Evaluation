package ma.projet.classes;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employe {
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Employe))
            return false;
        Employe employe = (Employe) o;
        return getId() == employe.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String prenom;
    private String telephone;

    @OneToMany(mappedBy = "chefProjet", fetch = FetchType.EAGER)
    private List<Projet> projetsGeres;

    @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<EmployeTache> employeTaches = new HashSet<>();
}