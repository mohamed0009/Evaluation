package ma.projet.classes;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Projet {
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Projet))
            return false;
        Projet projet = (Projet) o;
        return getId() == projet.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @ManyToOne
    @JoinColumn(name = "chef_projet_id")
    private Employe chefProjet;

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Tache> taches = new HashSet<>();
}