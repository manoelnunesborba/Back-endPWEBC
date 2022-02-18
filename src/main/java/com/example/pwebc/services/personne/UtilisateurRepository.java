package com.example.pwebc.services.personne;

import com.example.pwebc.tables.personne.coordonnées;
import com.example.pwebc.tables.personne.utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<utilisateur, Long> {
    @Query("select r from utilisateur r where r.nomuser = ?1")
    utilisateur FindByUsername(String Username);

}
