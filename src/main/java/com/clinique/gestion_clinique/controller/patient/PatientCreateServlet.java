package com.clinique.gestion_clinique.controller.patient;

import com.clinique.gestion_clinique.config.AppConfig;
import com.clinique.gestion_clinique.entity.Patient;
import com.clinique.gestion_clinique.repository.PatientDAO;
import com.clinique.gestion_clinique.service.PatientService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/patients/create")
public class PatientCreateServlet extends HttpServlet {

    private PatientService patientService;

    @Override
    public void init() {

        PatientDAO patientDAO =
                AppConfig.patientDAO();

        patientService =
                new PatientService(patientDAO);
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.getRequestDispatcher(
                "/WEB-INF/views/patients/create.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String nom =
                request.getParameter("nom");

        String prenom =
                request.getParameter("prenom");

        String dateNaissance =
                request.getParameter("dateNaissance");

        String numSecu =
                request.getParameter("numSecu");

        String tension =
                request.getParameter("tension");

        int frequenceCardiaque =
                Integer.parseInt(
                        request.getParameter(
                                "frequenceCardiaque"
                        )
                );

        double temperature =
                Double.parseDouble(
                        request.getParameter(
                                "temperature"
                        )
                );

        int frequenceRespiratoire =
                Integer.parseInt(
                        request.getParameter(
                                "frequenceRespiratoire"
                        )
                );

        Patient patient = new Patient();

        patient.setNom(nom);
        patient.setPrenom(prenom);

        patient.setDateNaissance(
                LocalDate.parse(dateNaissance)
        );

        patient.setNumSecu(numSecu);
        patient.setTension(tension);

        patient.setFrequenceCardiaque(
                frequenceCardiaque
        );

        patient.setTemperature(
                temperature
        );

        patient.setFrequenceRespiratoire(
                frequenceRespiratoire
        );

        patientService.createPatient(patient);

        response.sendRedirect(
                request.getContextPath() + "/patients"
        );
    }
}