package com.clinique.gestion_clinique.controller.generaliste;

import com.clinique.gestion_clinique.config.AppConfig;
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
import java.util.UUID;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/generaliste/consultation")
public class ConsultationServlet extends HttpServlet {

        private PatientService patientService;
        private ConsultationService consultationService;

        @Override
        public void init() {

                PatientDAO patientDAO = AppConfig.patientDAO();

                ConsultationDAO consultationDAO = AppConfig.consultationDAO();

                /**
                 * PatientService travaille uniquement
                 * avec PatientDAO.
                 */
                patientService = new PatientService(patientDAO);

                /**
                 * ConsultationService travaille avec
                 * ConsultationDAO + PatientDAO.
                 */
                consultationService = new ConsultationService(
                                consultationDAO,
                                patientDAO);
        }

        // @Override
        // protected void doGet(
        // HttpServletRequest request,
        // HttpServletResponse response) throws ServletException, IOException {

        // String patientIdParam = request.getParameter("patientId");

        // /**
        // * Vérification du paramètre patientId.
        // */
        // if (patientIdParam == null ||
        // patientIdParam.isBlank()) {

        // response.sendRedirect(
        // request.getContextPath()
        // + "/generaliste/patients"
        // + "?error=Patient+invalide");

        // return;
        // }

        // Long patientId;

        // try {

        // patientId = Long.parseLong(patientIdParam);

        // } catch (NumberFormatException exception) {

        // response.sendRedirect(
        // request.getContextPath()
        // + "/generaliste/patients"
        // + "?error=Patient+invalide");

        // return;
        // }

        // /**
        // * Recherche du patient.
        // */
        // Optional<Patient> patientOptional = patientService.findById(patientId);

        // if (patientOptional.isEmpty()) {

        // response.sendRedirect(
        // request.getContextPath()
        // + "/generaliste/patients"
        // + "?error=Patient+introuvable");

        // return;
        // }

        // Patient patient = patientOptional.get();

        // /**
        // * Envoie le patient vers la JSP.
        // */
        // request.setAttribute(
        // "patient",
        // patient);

        // request.getRequestDispatcher(
        // "/WEB-INF/views/generaliste/consultation-form.jsp").forward(
        // request,
        // response);
        // }

        @Override
        protected void doGet(
                        HttpServletRequest request,
                        HttpServletResponse response)
                        throws ServletException, IOException {

                // ==========================================
                // CSRF TOKEN
                // ==========================================

                HttpSession session = request.getSession();

                if (session.getAttribute("csrfToken") == null) {

                        session.setAttribute(
                                        "csrfToken",
                                        UUID.randomUUID().toString());
                }

                // ==========================================
                // RÉCUPÉRER LE PATIENT
                // ==========================================

                String patientIdParam = request.getParameter("patientId");

                if (patientIdParam == null ||
                                patientIdParam.isBlank()) {

                        response.sendRedirect(
                                        request.getContextPath()
                                                        + "/generaliste/patients"
                                                        + "?error=Patient+invalide");

                        return;
                }

                Long patientId;

                try {

                        patientId = Long.parseLong(patientIdParam);

                } catch (NumberFormatException exception) {

                        response.sendRedirect(
                                        request.getContextPath()
                                                        + "/generaliste/patients"
                                                        + "?error=Patient+invalide");

                        return;
                }

                Optional<Patient> patientOptional = patientService.findById(patientId);

                if (patientOptional.isEmpty()) {

                        response.sendRedirect(
                                        request.getContextPath()
                                                        + "/generaliste/patients"
                                                        + "?error=Patient+introuvable");

                        return;
                }

                Patient patient = patientService.findById(patientId)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Patient introuvable."));
                //
                // Patient patient = patientOptional.get();

                // request.setAttribute(
                // "patient",
                // patient);

                // request.getRequestDispatcher(
                // "/WEB-INF/views/generaliste/consultation-form.jsp").forward(request,
                // response);

                // ======================================
                // LE PATIENT PASSE EN COURS
                // ======================================

                if ("EN_ATTENTE".equals(patient.getStatut())) {

                        patient.setStatut("EN_COURS");

                        patientService.update(patient);
                }

                // ======================================
                // ENVOYER LE PATIENT À LA JSP
                // ======================================

                request.setAttribute(
                                "patient",
                                patient);

                request.getRequestDispatcher(
                                "/WEB-INF/views/generaliste/consultation-form.jsp").forward(
                                                request,
                                                response);
        }

        @Override
        protected void doPost(
                        HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {

                /**
                 * 1. Récupérer patientId.
                 */
                String patientIdParam = request.getParameter("patientId");

                Long patientId;

                try {

                        if (patientIdParam == null ||
                                        patientIdParam.isBlank()) {

                                throw new NumberFormatException();
                        }

                        patientId = Long.parseLong(patientIdParam);

                } catch (NumberFormatException exception) {

                        response.sendRedirect(
                                        request.getContextPath()
                                                        + "/generaliste/patients"
                                                        + "?error=Patient+invalide");

                        return;
                }

                /**
                 * 2. Récupérer les données du formulaire.
                 */
                String motif = request.getParameter("motif");

                String observations = request.getParameter("observations");

                String diagnostic = request.getParameter("diagnostic");

                String traitement = request.getParameter("traitement");

                /**
                 * 3. Récupérer la session.
                 */
                HttpSession session = request.getSession(false);

                if (session == null) {

                        response.sendRedirect(
                                        request.getContextPath()
                                                        + "/login");

                        return;
                }

                /**
                 * 4. Récupérer le médecin connecté.
                 */
                Utilisateur medecin = (Utilisateur) session.getAttribute(
                                "utilisateur");

                if (medecin == null) {

                        response.sendRedirect(
                                        request.getContextPath()
                                                        + "/login");

                        return;
                }

                try {

                        /**
                         * 5. Business logic.
                         *
                         * Le Service décide :
                         * - si le patient existe
                         * - s'il a déjà été consulté
                         * - si les champs sont valides
                         * - médecin connecté
                         * - coût = 150 DH
                         * - statut = TERMINEE
                         */
                        consultationService.cloturer(
                                        patientId,
                                        medecin,
                                        motif,
                                        observations,
                                        diagnostic,
                                        traitement);

                        /**
                         * 6. PRG
                         *
                         * POST → Redirect → GET
                         */
                        response.sendRedirect(
                                        request.getContextPath()
                                                        + "/generaliste/patients");

                } catch (
                                IllegalArgumentException | IllegalStateException exception) {

                        /**
                         * Une erreur métier est arrivée.
                         *
                         * On recharge le patient
                         * pour réafficher le formulaire.
                         */
                        Optional<Patient> patientOptional = patientService.findById(patientId);

                        if (patientOptional.isPresent()) {

                                request.setAttribute(
                                                "patient",
                                                patientOptional.get());
                        }

                        /**
                         * Message d'erreur.
                         */
                        request.setAttribute(
                                        "error",
                                        exception.getMessage());

                        /**
                         * Garder les anciennes valeurs
                         * du formulaire.
                         */
                        request.setAttribute(
                                        "motif",
                                        motif);

                        request.setAttribute(
                                        "observations",
                                        observations);

                        request.setAttribute(
                                        "diagnostic",
                                        diagnostic);

                        request.setAttribute(
                                        "traitement",
                                        traitement);

                        /**
                         * Retour au formulaire.
                         */
                        request.getRequestDispatcher(
                                        "/WEB-INF/views/generaliste/consultation-form.jsp").forward(
                                                        request,
                                                        response);
                }
        }
}