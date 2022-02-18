package com.example.pwebc.services.personne;

import com.example.pwebc.tables.personne.coordonnées;
import com.example.pwebc.tables.personne.utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface coordonnéesRepository extends JpaRepository<coordonnées, Long> {
}
