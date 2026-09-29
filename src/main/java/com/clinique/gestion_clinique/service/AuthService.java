package com.clinique.gestion_clinique.service;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {

    public boolean verifyPassword(String passwordInput, String storedHashedPassword) {
        
        return BCrypt.checkpw(passwordInput, storedHashedPassword);
    }
}