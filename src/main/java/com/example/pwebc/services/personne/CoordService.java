package com.example.pwebc.services.personne;


import com.example.pwebc.tables.personne.coordonnées;
import com.example.pwebc.tables.personne.utilisateur;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class CoordService {
    private final coordonnéesRepository coRepo;


    public CoordService(coordonnéesRepository coRepo) {
        this.coRepo = coRepo;
    }


    public void addCoord(coordonnées cord) {
        coRepo.save(cord);

    }

    public List<coordonnées> getCoordUser(utilisateur usr) {
        return new ArrayList(usr.getCoord());
    }
}
