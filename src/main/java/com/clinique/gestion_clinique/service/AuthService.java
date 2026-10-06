package com.clinique.gestion_clinique.service;

import com.clinique.gestion_clinique.config.ConnectionManager;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthService {

    public boolean authenticate(String email, String passwordInput) {
        String sql = "SELECT mot_de_passe FROM utilisateur WHERE email = ?";

        //لضمان إرجاع الاتصال للـ Pool أوتوماتيكياً
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("mot_de_passe");
                    return BCrypt.checkpw(passwordInput, storedHash);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}