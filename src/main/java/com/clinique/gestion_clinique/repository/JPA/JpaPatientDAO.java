package com.clinique.gestion_clinique.repository.JPA;

import com.clinique.gestion_clinique.config.JpaUtil;
import com.clinique.gestion_clinique.entity.Patient;
import com.clinique.gestion_clinique.repository.PatientDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class JpaPatientDAO implements PatientDAO {

    @Override
    public Optional<Patient> findById(Long id) {

        EntityManager em = JpaUtil.createEntityManager();

        try {
            Patient patient = em.find(Patient.class, id);
            return Optional.ofNullable(patient);

        } finally {
            em.close();
        }
    }

    @Override
    public List<Patient> findAll() {

        EntityManager em = JpaUtil.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT p FROM Patient p ORDER BY p.heureArrivee DESC",
                    Patient.class).getResultList();

        } finally {
            em.close();
        }
    }

    @Override
    public List<Patient> findByDate(LocalDate date) {

        EntityManager em = JpaUtil.createEntityManager();

        try {

            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.plusDays(1).atStartOfDay();

            return em.createQuery(
                    """
                            SELECT p
                            FROM Patient p
                            WHERE p.heureArrivee >= :start
                            AND p.heureArrivee < :end
                            ORDER BY p.heureArrivee DESC
                            """,
                    Patient.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();

        } finally {
            em.close();
        }
    }

    @Override
    public Patient save(Patient patient) {

        EntityManager em = JpaUtil.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {

            transaction.begin();

            em.persist(patient);

            transaction.commit();

            return patient;

        } catch (RuntimeException e) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }

    @Override
    public void update(Patient patient) {

        EntityManager em = JpaUtil.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {

            transaction.begin();

            em.merge(patient);

            transaction.commit();

        } catch (RuntimeException e) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }

    @Override
    public List<Patient> findByStatut(String statut) {
        EntityManager em = JpaUtil.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT p FROM Patient p WHERE p.statut = :statut",
                    Patient.class)
                    .setParameter("statut", statut)
                    .getResultList();

        } finally {
            em.close();
        }
    }
}