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
import java.time.format.DateTimeParseException;

@WebServlet("/infirmier/patients/nouveau")
public class PatientCreateServlet extends HttpServlet {

    private PatientService patientService;

    @Override
    public void init() {

        PatientDAO patientDAO = AppConfig.patientDAO();

        patientService = new PatientService(patientDAO);
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher(
                "/WEB-INF/views/infirmier/patient-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String dateNaissanceParam = request.getParameter("dateNaissance");

        String numSecu = request.getParameter("numSecu");

        String tension = request.getParameter("tension");

        String frequenceCardiaqueParam = request.getParameter("frequenceCardiaque");

        String temperatureParam = request.getParameter("temperature");

        String frequenceRespiratoireParam = request.getParameter("frequenceRespiratoire");

        try {

            LocalDate dateNaissance = LocalDate.parse(dateNaissanceParam);

            int frequenceCardiaque = Integer.parseInt(frequenceCardiaqueParam);

            double temperature = Double.parseDouble(temperatureParam);

            int frequenceRespiratoire = Integer.parseInt(frequenceRespiratoireParam);

            Patient patient = patientService.enregistrer(
                    nom,
                    prenom,
                    dateNaissance,
                    numSecu,
                    tension,
                    frequenceCardiaque,
                    temperature,
                    frequenceRespiratoire);

            /**
             * PRG = Post / Redirect / Get
             */
            response.sendRedirect(
                    request.getContextPath()
                            + "/infirmier/patients");

        } catch (DateTimeParseException e) {

            request.setAttribute(
                    "error",
                    "Date de naissance invalide.");

            reloadForm(request);

            request.getRequestDispatcher(
                    "/WEB-INF/views/infirmier/patient-form.jsp").forward(request, response);

        } catch (NumberFormatException e) {

            request.setAttribute(
                    "error",
                    "Les valeurs numériques sont invalides.");

            reloadForm(request);

            request.getRequestDispatcher(
                    "/WEB-INF/views/infirmier/patient-form.jsp").forward(request, response);

        } catch (IllegalArgumentException e) {

            request.setAttribute(
                    "error",
                    e.getMessage());

            reloadForm(request);

            request.getRequestDispatcher(
                    "/WEB-INF/views/infirmier/patient-form.jsp").forward(request, response);
        }
    }

    private void reloadForm(HttpServletRequest request) {

        request.setAttribute(
                "nom",
                request.getParameter("nom"));

        request.setAttribute(
                "prenom",
                request.getParameter("prenom"));

        request.setAttribute(
                "dateNaissance",
                request.getParameter("dateNaissance"));

        request.setAttribute(
                "numSecu",
                request.getParameter("numSecu"));

        request.setAttribute(
                "tension",
                request.getParameter("tension"));

        request.setAttribute(
                "frequenceCardiaque",
                request.getParameter("frequenceCardiaque"));

        request.setAttribute(
                "temperature",
                request.getParameter("temperature"));

        request.setAttribute(
                "frequenceRespiratoire",
                request.getParameter("frequenceRespiratoire"));
    }
}