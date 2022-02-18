package com.example.pwebc;

import com.example.pwebc.services.personne.UtilisateurService;
import com.example.pwebc.tables.personne.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;

@SpringBootApplication

public class AuroreApplication {
    @Autowired
    public static void main(String[] args) {
        SpringApplication.run(AuroreApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    CommandLineRunner commandLineRunner(UtilisateurService as){
        return args -> {

            //Users and roles
            utilisateur test = new utilisateur(Long.parseLong("2"),"yacine", "4321", new ArrayList<>(), new ArrayList<>());
            as.adduser(new utilisateur( Long.parseLong("1"),"manoel", "1234", new ArrayList<>(), new ArrayList<>()));
            as.adduser(test);
            as.adduser(new utilisateur(Long.parseLong("3"),"ahmad", "syfsa", new ArrayList<>(), new ArrayList<>()));
            as.saverole(new Role("CHEF"));
            as.saverole(new Role("AGENT"));
            as.addRoleToUser(Long.parseLong("1"),"CHEF");
            as.addRoleToUser(Long.parseLong("2"),"CHEF");
            as.addRoleToUser(Long.parseLong("3"),"CHEF");
            //prestations posibles
        };
    }

}