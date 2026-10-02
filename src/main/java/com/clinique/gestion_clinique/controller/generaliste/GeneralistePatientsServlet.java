package com.clinique.gestion_clinique.controller.generaliste;

import com.clinique.gestion_clinique.config.AppConfig;
import com.clinique.gestion_clinique.entity.Patient;
import com.clinique.gestion_clinique.repository.ConsultationDAO;
import com.clinique.gestion_clinique.repository.PatientDAO;
import com.clinique.gestion_clinique.service.ConsultationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/generaliste/patients")
public class GeneralistePatientsServlet
        extends HttpServlet {

    private ConsultationService consultationService;

    @Override
    public void init() {

        PatientDAO patientDAO = AppConfig.patientDAO();

        ConsultationDAO consultationDAO = AppConfig.consultationDAO();

        consultationService = new ConsultationService(
                consultationDAO,
                patientDAO);
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        List<Patient> patients = consultationService.patientsEnAttente();

        request.setAttribute(
                "patients",
                patients);

        request.getRequestDispatcher(
                "/WEB-INF/views/generaliste/attente.jsp").forward(
                        request,
                        response);
    }
}