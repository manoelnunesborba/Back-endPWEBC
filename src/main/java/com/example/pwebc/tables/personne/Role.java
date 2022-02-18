package com.example.pwebc.tables.personne;

import javax.persistence.*;

@Entity
public class Role {
    @Id
    @SequenceGenerator(
            name = "sequence",
            sequenceName = "seq_role",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_role"
    )
    private Long id;
    private String nom;

    public Role(Long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Role() {

    }

    public Role(String nom) {
        this.nom = nom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
