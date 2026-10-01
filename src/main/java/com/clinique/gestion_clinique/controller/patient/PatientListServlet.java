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
import java.util.List;

@WebServlet("/infirmier/patients")
public class PatientListServlet extends HttpServlet {

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

                List<Patient> patients = patientService.patientsDuJour();

                request.setAttribute(
                                "patients",
                                patients);

                request.getRequestDispatcher(
                                "/WEB-INF/views/infirmier/patients.jsp").forward(request, response);
        }
}