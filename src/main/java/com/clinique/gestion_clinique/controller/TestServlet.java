package com.clinique.gestion_clinique.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestServlet {

    @GetMapping("/")
    public String index() {
        return "Bienvenue sur l'application de Gestion de Clinique !";
    }
}