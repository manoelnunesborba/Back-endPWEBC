package com.example.pwebc.services.personne;

import com.example.pwebc.tables.personne.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface role extends JpaRepository<Role, Long> {

    @Query("select r from Role r where r.nom = ?1")
    Role findByNom(String nom);
}
