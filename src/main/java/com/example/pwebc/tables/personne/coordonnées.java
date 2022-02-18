package com.example.pwebc.tables.personne;

import javax.persistence.*;
@Entity
@Table
public class coordonnées {
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
    private Long X;
    private Long Y;

    public coordonnées() {
    }

    public coordonnées(Long x, Long y) {
        X = x;
        Y = y;
    }

    public coordonnées(Long id, Long x, Long y) {
        this.id = id;
        X = x;
        Y = y;
    }

    public Long getY() {
        return Y;
    }

    public void setY(Long y) {
        Y = y;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getX() {
        return X;
    }

    public void setX(Long x) {
        X = x;
    }

    @Override
    public String toString() {
        return "coordonnées{" +
                "id=" + id +
                ", X=" + X +
                ", Y=" + Y +
                '}';
    }
}
