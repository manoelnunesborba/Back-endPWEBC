package com.example.pwebc.services.personne;

import com.example.pwebc.tables.personne.Role;
import com.example.pwebc.tables.personne.coordonnées;
import com.example.pwebc.tables.personne.utilisateur;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UtilisateurService implements UserDetailsService {
    private final UtilisateurRepository utilisateurRepository;
    private final coordonnéesRepository core;
    private final CoordService servRepo;
    private final role roleRepo;
    private final PasswordEncoder PE;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        utilisateur ag = utilisateurRepository.FindByUsername(username);
        if(ag== null ){
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        }else{
            log.error("User found {}", username);

        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        ag.getRoles().forEach(role -> {
            authorities .add(new SimpleGrantedAuthority(role.getNom()));
        });
        return new org.springframework.security.core.userdetails.User(ag.getNomuser(),ag.getMdp(), authorities);
    }

    public utilisateur getAgent(String username){
        return utilisateurRepository.FindByUsername(username);
    }
    public UtilisateurService(UtilisateurRepository ageRepo, coordonnéesRepository core, CoordService servRepo, role roleRepo, PasswordEncoder pe) {
        this.utilisateurRepository = ageRepo;
        this.core = core;
        this.servRepo = servRepo;
        this.roleRepo = roleRepo;
        PE = pe;
    }

    public List<utilisateur> getPerformerAge(){
        return utilisateurRepository.findAll();
    }

    public void adduser(utilisateur usr){
        usr.setMdp(PE.encode(usr.getMdp()));
        utilisateurRepository.save(usr);
    }

    public Role saverole(Role role){
        return roleRepo.save(role);
    }
    public void addRoleToUser(Long id, String rolename){
        Optional<utilisateur> ag = utilisateurRepository.findById(id);

        Role rl = roleRepo.findByNom(rolename);
        ag.get().getRoles().add(rl);

    }
    public utilisateur getagent(Long id){
            return utilisateurRepository.getById(id);
    }
    public void saveCoord(coordonnées cr){
        core.save(cr);
    }
    public void addCoordToUser(Long id, Long idcord){
        Optional<utilisateur> ag = utilisateurRepository.findById(id);
        Optional<coordonnées> coord = core.findById(idcord);
        ag.get().getCoord().add(coord.get());

    }
}
