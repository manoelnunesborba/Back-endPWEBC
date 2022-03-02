package com.example.pwebc.tables.personne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;


@Entity
@Table
public class utilisateur {
    @Id
    @SequenceGenerator(
            name = "sequence",
            sequenceName = "seq_vis",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_vis"
    )
    private Long id;
    private String nomuser;
    private String mdp;
    @ManyToMany(fetch = LAZY)
    private Collection<Role> roles = new ArrayList<>();
    @ManyToMany(fetch = EAGER)
    private Collection<coordonnées> coord = new ArrayList<coordonnées>();


    public utilisateur() {

    }

    public utilisateur(Long id, String nomuser, String mdp, Collection<Role> roles, Collection<coordonnées> coord) {
        this.id = id;
        this.nomuser = nomuser;
        this.mdp = mdp;
        this.roles = roles;
        this.coord = coord;
    }

    public utilisateur(String nomuser, String mdp, Collection<Role> roles, Collection<coordonnées> coord) {
        this.nomuser = nomuser;
        this.mdp = mdp;
        this.roles = roles;
        this.coord = coord;
    }

    public Collection<coordonnées> getCoord() {
        return coord;
    }

    public void setCoord(Collection<coordonnées> coord) {
        this.coord = coord;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomuser() {
        return nomuser;
    }

    public void setNomuser(String nomuser) {
        this.nomuser = nomuser;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    @Override
    public String toString() {
        return "utilisateur{" +
                "id=" + id +
                ", nomuser='" + nomuser + '\'' +
                ", mdp='" + mdp + '\'' +
                '}';
    }
}
