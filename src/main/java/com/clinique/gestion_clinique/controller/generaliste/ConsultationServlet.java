package com.clinique.gestion_clinique.controller.generaliste;

import com.clinique.gestion_clinique.config.AppConfig;
import com.clinique.gestion_clinique.entity.Consultation;
import com.clinique.gestion_clinique.entity.Patient;
import com.clinique.gestion_clinique.entity.Utilisateur;
import com.clinique.gestion_clinique.repository.ConsultationDAO;
import com.clinique.gestion_clinique.repository.PatientDAO;
import com.clinique.gestion_clinique.service.ConsultationService;
import com.clinique.gestion_clinique.service.PatientService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/generaliste/consultation")
public class ConsultationServlet
        extends HttpServlet {

    private PatientService patientService;
    private ConsultationService consultationService;

    @Override
    public void init() {

        PatientDAO patientDAO =
                AppConfig.patientDAO();

        ConsultationDAO consultationDAO =
                AppConfig.consultationDAO();

        patientService =
                new PatientService(
                        patientDAO,
                        consultationDAO
                );

        consultationService =
                new ConsultationService(
                        consultationDAO,
                        patientDAO
                );
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String patientIdParam =
                request.getParameter("patientId");

        if (patientIdParam == null ||
                patientIdParam.isBlank()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/generaliste/patients"
                            + "?error=Patient+invalide"
            );

            return;
        }

        Long patientId;

        try {

            patientId =
                    Long.parseLong(
                            patientIdParam
                    );

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/generaliste/patients"
                            + "?error=Patient+invalide"
            );

            return;
        }

        Optional<Patient> patientOptional =
                patientService.findById(patientId);

        if (patientOptional.isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/generaliste/patients"
                            + "?error=Patient+introuvable"
            );

            return;
        }

        Patient patient =
                patientOptional.get();

        request.setAttribute(
                "patient",
                patient
        );

        request.getRequestDispatcher(
                "/WEB-INF/views/generaliste/consultation-form.jsp"
        ).forward(
                request,
                response
        );
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String patientIdParam =
                request.getParameter("patientId");

        Long patientId;

        try {

            patientId =
                    Long.parseLong(
                            patientIdParam
                    );

        } catch (Exception e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/generaliste/patients"
                            + "?error=Patient+invalide"
            );

            return;
        }

        String motif =
                request.getParameter("motif");

        String observations =
                request.getParameter("observations");

        String diagnostic =
                request.getParameter("diagnostic");

        String traitement =
                request.getParameter("traitement");

        HttpSession session =
                request.getSession(false);

        if (session == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login"
            );

            return;
        }

        Utilisateur medecin =
                (Utilisateur) session.getAttribute(
                        "utilisateur"
                );

        if (medecin == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login"
            );

            return;
        }

        try {

            Consultation consultation =
                    consultationService.cloturer(
                            patientId,
                            medecin,
                            motif,
                            observations,
                            diagnostic,
                            traitement
                    );

            // PRG
            response.sendRedirect(
                    request.getContextPath()
                            + "/generaliste/patients"
            );

        } catch (IllegalArgumentException |
                 IllegalStateException e) {

            Optional<Patient> patientOptional =
                    patientService.findById(
                            patientId
                    );

            if (patientOptional.isPresent()) {

                request.setAttribute(
                        "patient",
                        patientOptional.get()
                );
            }

            request.setAttribute(
                    "error",
                    e.getMessage()
            );

            request.setAttribute(
                    "motif",
                    motif
            );

            request.setAttribute(
                    "observations",
                    observations
            );

            request.setAttribute(
                    "diagnostic",
                    diagnostic
            );

            request.setAttribute(
                    "traitement",
                    traitement
            );

            request.getRequestDispatcher(
                    "/WEB-INF/views/generaliste/consultation-form.jsp"
            ).forward(
                    request,
                    response
            );
        }
    }
}