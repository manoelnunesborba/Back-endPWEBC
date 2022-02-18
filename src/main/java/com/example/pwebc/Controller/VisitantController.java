package com.example.pwebc.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.pwebc.services.personne.*;
import com.example.pwebc.services.personne.UtilisateurService;
import com.example.pwebc.tables.personne.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("controller")
@CrossOrigin("*")
@Slf4j
public class VisitantController {
    private final UtilisateurService ageSer;
    private final role roleServ;
    private final CoordService cose;

    @Autowired
    public VisitantController(UtilisateurService ageSer, role roleServ, CoordService cose) {
        this.ageSer = ageSer;
        this.roleServ = roleServ;
        this.cose = cose;
    }


    @GetMapping("utilisateur")
    public ResponseEntity<List<utilisateur>> getPerformerProfileMySQLAge(){
        return ResponseEntity.ok().body(ageSer.getPerformerAge());
    }

    @GetMapping("getcoord")
    public List<coordonnées> getCoordUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("getcoord function");
        String autheadet = request.getHeader(AUTHORIZATION);
        if(autheadet!= null && autheadet.startsWith("Baerer ")){
            try {
                String refresh = autheadet.substring("Baerer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verif = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verif.verify(refresh);
                String username = decodedJWT.getSubject();
                utilisateur usr = ageSer.getAgent(username);
                return cose.getCoordUser(usr);

            }catch(Exception ex){
                response.setHeader("error", ex.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String,String> errors = new HashMap<>();
                errors.put("error_message", ex.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), errors);
            }
        }else{
            throw new RuntimeException("Refresh token isMissing");
        }
        return null;
    }


    @PostMapping(path = "addRole",
            consumes = MediaType.APPLICATION_JSON_VALUE, // il y a une erreur lors que j'essaye d'ajouter...
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void addRole(@RequestBody Role rl){
        Role ts = ageSer.saverole(rl);
    }
    @PostMapping(path = "addRoleToAgent",
            consumes = MediaType.APPLICATION_JSON_VALUE, // il y a une erreur lors que j'essaye d'ajouter...
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void addRoletoAgent(@RequestBody RoleToAgentForm rtaf){
        ageSer.addRoleToUser(rtaf.getIdUser(), rtaf.getRoleNmae());

    }
    @PostMapping(path = "createaccount",
            consumes = APPLICATION_JSON_VALUE, // il y a une erreur lors que j'essaye d'ajouter...
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void addRole(@RequestBody utilisateur usr){
        ageSer.adduser(usr);
    }








    @GetMapping("refreshToken")
    public void getNewToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String autheadet = request.getHeader(AUTHORIZATION);
        if(autheadet!= null && autheadet.startsWith("Baerer ")){
            try {
                String refresh = autheadet.substring("Baerer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verif = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verif.verify(refresh);
                String username = decodedJWT.getSubject();
                utilisateur usr = ageSer.getAgent(username);
                String accesTok = JWT.create()
                        .withSubject(usr.getNomuser())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", usr.getRoles().stream().map(Role::getNom).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String,String> tokens = new HashMap<>();
                tokens.put("accesToken", accesTok);
                tokens.put("refreshToken", refresh);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }catch(Exception ex){
                response.setHeader("error", ex.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String,String> errors = new HashMap<>();
                errors.put("error_message", ex.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), errors);
            }
        }else{
            throw new RuntimeException("Refresh token isMissing");
        }
    }

    


}
@Data
class RoleToAgentForm{
    private Long idUser;
    private String RoleNmae;

}