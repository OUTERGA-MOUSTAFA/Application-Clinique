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

        // @Override
        // protected void doGet(
        // HttpServletRequest request,
        // HttpServletResponse response) throws ServletException, IOException {

        // List<Patient> patients = consultationService.patientsEnAttente();

        // request.setAttribute(
        // "patients",
        // patients);

        // request.getRequestDispatcher(
        // "/WEB-INF/views/generaliste/attente.jsp").forward(
        // request,
        // response);
        // }

        @Override
        protected void doGet(
                        HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {

                /**
                 * ==========================================
                 * 1. Récupérer le statut demandé
                 * ==========================================
                 */
                String statut = request.getParameter("statut");

                if (statut == null || statut.isBlank()) {
                        statut = "EN_ATTENTE"; // par défaut
                }

                /**
                 * ==========================================
                 * 2. Filtrer selon le statut
                 * ==========================================
                 */
                List<Patient> patients;

                switch (statut) {
                        case "EN_COURS":
                                patients = consultationService.patientsParStatut("EN_COURS");
                                break;
                        case "TERMINEE":
                                patients = consultationService.patientsParStatut("TERMINEE");
                                break;
                        case "EN_ATTENTE":
                        default:
                                patients = consultationService.patientsParStatut("EN_ATTENTE");
                                statut = "EN_ATTENTE";
                                break;
                }

                /**
                 * ==========================================
                 * 3. Envoyer à la JSP
                 * ==========================================
                 */
                request.setAttribute("patients", patients);
                request.setAttribute("statut", statut);

                request.getRequestDispatcher(
                                "/WEB-INF/views/generaliste/attente.jsp").forward(
                                                request,
                                                response);
        }
}