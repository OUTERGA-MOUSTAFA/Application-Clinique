package com.clinique.gestion_clinique.repository.JPA;

import com.clinique.gestion_clinique.config.JpaUtil;
import com.clinique.gestion_clinique.entity.Consultation;
import com.clinique.gestion_clinique.repository.ConsultationDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class JpaConsultationDAO implements ConsultationDAO {

    @Override
    public Consultation save(Consultation consultation) {

        EntityManager em = JpaUtil.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {

            transaction.begin();

            em.persist(consultation);

            transaction.commit();

            return consultation;

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
    public Optional<Consultation> findByPatient(Long patientId) {

        EntityManager em = JpaUtil.createEntityManager();

        try {

            List<Consultation> consultations = em.createQuery(
                    """
                    SELECT c
                    FROM Consultation c
                    WHERE c.patient.id = :patientId
                    """,
                    Consultation.class
            )
            .setParameter("patientId", patientId)
            .getResultList();

            return consultations.stream().findFirst();

        } finally {
            em.close();
        }
    }

    @Override
    public List<Consultation> findAll() {

        EntityManager em = JpaUtil.createEntityManager();

        try {

            return em.createQuery(
                    "SELECT c FROM Consultation c",
                    Consultation.class
            ).getResultList();

        } finally {
            em.close();
        }
    }
}